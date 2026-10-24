package com.lmy.controller;

import com.lmy.pojo.Clazz;
import com.lmy.pojo.ClazzQueryParam;
import com.lmy.pojo.PageResult;
import com.lmy.pojo.Result;
import com.lmy.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    /**
     * 班级列表查询
     */
    @GetMapping
    public Result getClazz(ClazzQueryParam clazzQueryParam) {
        log.info("请求分页参数：{}", clazzQueryParam);

        PageResult<Clazz> pageResult = clazzService.getClazz(clazzQueryParam);

        return Result.success(pageResult);

    }

    /**
     * 添加班级
     */
    @PostMapping
    public Result addClazz(@RequestBody Clazz clazz) {
        log.info("请求参数：{}", clazz);
        clazzService.addClazz(clazz);
        return Result.success();

    }

    /**
     * 根据id删除班级
     */
    @DeleteMapping("/{id}")
    public Result deleteClazz(@PathVariable("id") Integer id) {
        log.info("请求参数：{}", id);
        clazzService.deleteClazz(id);
        return Result.success();
    }
    /**
     * 根据id查询班级
     */
    @GetMapping("/{id}")
    public Result getClazzById(@PathVariable("id") Integer id) {
        log.info("请求参数：{}", id);
        Clazz clazz = clazzService.getClazzById(id);
        return Result.success(clazz);
    }
    /**
     * 更新班级
     */
    @PutMapping
    public Result updateClazz(@RequestBody Clazz clazz) {
        log.info("请求参数：{}", clazz);
        clazzService.updateClazz(clazz);
        return Result.success();
    }
    /**
     * 查询所有班级
     */
    @GetMapping("/list")
    public Result getAllClazz() {
        log.info("请求参数：{}", "");
        List<Clazz> clazzList = clazzService.getAllClazz();
        return Result.success(clazzList);
    }
}
