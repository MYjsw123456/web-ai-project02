package com.lmy.aop;

import com.lmy.anno.Log;
import com.lmy.mapper.OperateLogMapper;
import com.lmy.pojo.OperateLog;
import com.lmy.utils.CurrentHolder;
import com.lmy.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面
 * 拦截 com.lmy.controller 包下被 @Log 标记的增、删、改接口，
 * 记录操作人、操作时间、目标类全类名、方法名、方法参数、返回值以及方法执行耗时
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    /** operate_log 表中 method_params、return_value 字段的字符长度上限 */
    private static final int MAX_CONTENT_LENGTH = 2000;

    @Autowired
    private OperateLogMapper operateLogMapper;

    /** 复用 Spring Boot 自动配置的 Jackson 3 序列化器 */
    @Autowired
    private JsonMapper jsonMapper;

    /**
     * 环绕通知：记录目标方法的执行信息并写入 operate_log 表
     *
     * @param pjp 连接点
     * @return 目标方法的返回值
     * @throws Throwable 目标方法抛出的异常原样抛出，不影响业务逻辑
     */
    @Around("execution(* com.lmy.controller..*(..)) && @annotation(com.lmy.anno.Log)")
    public Object recordOperateLog(ProceedingJoinPoint pjp) throws Throwable {
        //1.记录操作时间和开始时间
        LocalDateTime operateTime = LocalDateTime.now();
        long startTime = System.currentTimeMillis();

        Object result = null;
        Throwable error = null;
        try {
            //2.执行目标方法（增、删、改接口）
            result = pjp.proceed();
            return result;
        } catch (Throwable t) {
            error = t;
            throw t;
        } finally {
            //3.无论执行成功还是失败都记录日志，保证操作可追溯
            long costTime = System.currentTimeMillis() - startTime;
            saveOperateLog(pjp, operateTime, costTime, result, error);
        }
    }

    /**
     * 组装日志对象并保存到 operate_log 表
     */
    private void saveOperateLog(ProceedingJoinPoint pjp, LocalDateTime operateTime,
                                long costTime, Object result, Throwable error) {
        try {
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();

            OperateLog operateLog = new OperateLog();
            operateLog.setOperateEmpId(getCurrentEmpId());
            operateLog.setOperateTime(operateTime);
            operateLog.setClassName(method.getDeclaringClass().getName());
            operateLog.setMethodName(method.getName());
            operateLog.setMethodParams(truncate(toJson(pjp.getArgs())));
            operateLog.setReturnValue(truncate(error == null
                    ? toJson(result)
                    : "方法执行异常：" + error.getClass().getSimpleName() + " - " + error.getMessage()));
            operateLog.setCostTime(costTime);

            operateLogMapper.insert(operateLog);
            log.info("操作日志记录成功：{}#{}，耗时{}ms", operateLog.getClassName(), operateLog.getMethodName(), costTime);
        } catch (Exception e) {
            //日志记录失败不能影响业务接口的正常返回
            log.error("操作日志记录失败", e);
        }
    }

    /**
     * 从请求头 token 中解析当前操作人ID
     *
     * @return 操作人ID，解析失败时返回 null
     */
    private Integer getCurrentEmpId() {
        //1.用attributes获取request，然后从request中获取token，最后解析token获取操作人ID
/*        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            return null;
        }
        try {
            Claims claims = JwtUtils.parserToken(token);
            Object empId = claims.get("id");
            return empId == null ? null : Integer.valueOf(empId.toString());
        } catch (Exception e) {
            log.warn("解析令牌获取操作人失败：{}", e.getMessage());
            return null;
        }*/

        //2.从threadLocal中获取当前操作人ID
        return CurrentHolder.getCurrentId();
    }

    /**
     * 对象转JSON字符串，序列化失败时降级为toString
     */
    private String toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return jsonMapper.writeValueAsString(value);
        } catch (Exception e) {
            log.warn("操作日志内容序列化失败，降级为toString：{}", e.getMessage());
            return value instanceof Object[] args ? Arrays.toString(args) : String.valueOf(value);
        }
    }

    /**
     * 截断超长内容，避免超出数据库字段长度限制
     */
    private String truncate(String content) {
        if (content == null || content.length() <= MAX_CONTENT_LENGTH) {
            return content;
        }
        return content.substring(0, MAX_CONTENT_LENGTH - 3) + "...";
    }
}
