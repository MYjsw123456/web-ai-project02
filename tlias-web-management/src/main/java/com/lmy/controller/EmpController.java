package com.lmy.controller;


import com.lmy.pojo.Emp1;
import com.lmy.pojo.EmpQueryParam;
import com.lmy.pojo.PageResult;
import com.lmy.pojo.Result;
import com.lmy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    /**
     * 查询员工信息
     */
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result getEmp(EmpQueryParam empQueryParam){

        log.info("请求分页参数：{}",empQueryParam);
        PageResult<Emp1> pageResult = empService.getEmp(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp1 emp1){
        log.info("保存员工信息：{}",emp1);
        empService.save(emp1);
        return Result.success();
    }

    /**
     * 批量删除员工信息
     */
    @DeleteMapping()
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工：{}",ids);
        empService.delete(ids);
        return  Result.success();

    }

    /**
     * 查询全部员工（班主任下拉选项）
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询全部员工");
        List<Emp1> list = empService.findAll();
        return Result.success(list);
    }

    /**
     * 根据id查询回显员工信息
     */
    @GetMapping("{id}")
    public Result getEmpById(@PathVariable Integer id){

        Emp1 emp1 = empService.getEmpById(id);
        return Result.success(emp1);

    }

    /**
     * 修改员工信息
     */
    @PutMapping
    public Result putEmp(@RequestBody Emp1 emp1){
        log.info("修改员工信息：{}",emp1);
        empService.putEmp(emp1);

        return Result.success();

    }

}
