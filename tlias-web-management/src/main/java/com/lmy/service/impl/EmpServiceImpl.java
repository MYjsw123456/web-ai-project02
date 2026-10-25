package com.lmy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmy.mapper.EmpExprMapper;
import com.lmy.mapper.EmpLogMapper;
import com.lmy.mapper.EmpMapper;
import com.lmy.pojo.*;
import com.lmy.service.EmpLogService;
import com.lmy.service.EmpService;
import com.lmy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

/*    @Override
    public PageResult<Emp1> getEmp(Integer page, Integer pageSize) {

        PageResult<Emp1>  pageResult= new PageResult<>();
        Integer start = (page-1)*pageSize;
        pageResult.setRow(empMapper.getEmp(start,pageSize));
        pageResult.setTotal(empMapper.getCount());


        return  pageResult;
    }*/

    /**
     * 使用pageHelper实现分页
     * 注意事项：
     *         1.定义的sql语句后面不能加分号
     *         2.分页操作只对其后的第一个查询语句生效
     */
    public PageResult<Emp1> getEmp(EmpQueryParam empQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());

        //2.调用empMapper查询员工信息
        List<Emp1> emp = empMapper.getEmp(empQueryParam);
        Page<Emp1> p=(Page<Emp1>)emp;

        //3.返回结果
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Transactional(rollbackFor = Exception.class)  //事务管理 - 默认出现运行时异常RuntimeException才会回滚
    @Override
    public void save(Emp1 emp1) {
        try {
            //1.保存员工基本信息
            emp1.setCreateTime(LocalDateTime.now());
            emp1.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp1);

            //2.保存员工工作经历
            List<EmpExpr> exprList = emp1.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(expr -> {
                    expr.setEmpId(emp1.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {

            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),"新增员工"+emp1);
            empLogService.insertLog(empLog);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        //1.删除员工信息
        empMapper.deleteById(ids);

        //2.删除员工工作经历信息
        empExprMapper.deleteByEmpId(ids);
    }

    @Override
    public Emp1 getEmpById(Integer id) {
        //1.获取员工信息
        Emp1 emp1=empMapper.getEmpById(id);

        //2.获取员工经历信息
        List<EmpExpr> list= empExprMapper.getEmpExprById(id);
        emp1.setExprList(list);
        return emp1;
    }

    @Override
    public void putEmp(Emp1 emp1) {
        //1.修改员工信息
        emp1.setUpdateTime(LocalDateTime.now());
        empMapper.putEmp(emp1);

        //2.修改员工经历信息
        List<EmpExpr> exprList = emp1.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            //删除该员工原有的经历信息
            List<Integer> list = new ArrayList<>();
            list.add(emp1.getId());
            empExprMapper.deleteByEmpId(list);
            //添加修改之后的经历信息
            exprList.forEach(expr -> {
                expr.setEmpId(emp1.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
    }

    /**
     * 查询全部员工（班主任下拉选项）
     */
    @Override
    public List<Emp1> findAll() {
        return empMapper.findAll();
    }

    /**
     * 登录方法
     */
    @Override
    public LoginInfo login(Emp1 emp) {

        LoginInfo l = empMapper.getEmpByUsernameAndPassword(emp);

        if(l != null){
            //生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", l.getId());
            claims.put("username", l.getUsername());
            String token = JwtUtils.generateToken(claims);
            l.setToken(token);
            return l;
        }
        return null;
    }
}
