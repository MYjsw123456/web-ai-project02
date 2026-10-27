package com.lmy.controller;

import com.lmy.anno.Log;
import com.lmy.pojo.PageResult;
import com.lmy.pojo.Result;
import com.lmy.pojo.Student;
import com.lmy.pojo.StudentQueryParam;
import com.lmy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    /**
     * 学员列表查询
     */
    @GetMapping
    public Result getStudent(StudentQueryParam studentQueryParam) {
        log.info("请求参数：{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.getStudent(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 新增学员
     */
    @Log
    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        log.info("请求参数：{}", student);
        studentService.addStudent(student);
        return Result.success();
    }
    /**
     * 删除学员（支持单个和批量删除，多个id以逗号分隔）
     */
    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("删除学员：{}", ids);
        studentService.delete(ids);
        return Result.success();
    }
    /**
     * 根据id查询学员
     */
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Integer id) {
        log.info("请求参数：{}", id);
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }
    /**
     * 修改学员
     */
    @Log
    @PutMapping
    public Result updateStudent(@RequestBody Student student) {
        log.info("请求参数：{}", student);
        studentService.updateStudent(student);
        return Result.success();
    }
    /**
     * 学员违纪处理
     */
    @Log
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("请求参数：{}, {}", id, score);
        studentService.violation(id, score);
        return Result.success();
    }
}
