package com.lmy.controller;

import com.lmy.anno.Log;
import com.lmy.pojo.Dept;
import com.lmy.pojo.Result;
import com.lmy.service.DeptService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DeptController {

//    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    DeptService deptService;

    /**
     * 查询部门表
     */
//    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping("/depts")
    public Result list(){
        log.info("查询所有部门");
        List<Dept> list = deptService.findAll();

        return Result.success(list);
    }

    /**
     * 删除部门
     */

    //方式一：用原始的HttpServletRequest接收参数
/*    @DeleteMapping("/depts")
    public  Result deleteById(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        deptService.deleteById(id);
        return Result.success();
    }*/

    //方式二：用Spring提供的@RequestParam注解，将请求参数绑定给方法形参
/*    @DeleteMapping("/depts")
    public  Result deleteById(@RequestParam(value = "id",required = false) Integer id){
        deptService.deleteById(id);
        return Result.success();
    }*/

    //方式三：省略@RequestParam注解（前端传递的请求参数名和后端方法形参名一样）
    @Log
    @DeleteMapping("/depts")
    public  Result deleteById(Integer id){
        log.info("根据id删除部门：{}",id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增部门
     */
    @Log
    @PostMapping("/depts")
    public Result addDept(@RequestBody Dept dept){

        log.info("增加部门：{}",dept);
        deptService.addDept(dept);
        return Result.success();
    }

    /**
     * 根据id查询部门
     */
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer id){
        log.info("根据id查询部门：{}",id);
        Dept dept = deptService.getInfo(id);
        return Result.success(dept);
    }


    /**
     * 修改部门
     */
    @Log
    @PutMapping("/depts")
    public Result putDept(@RequestBody Dept dept){

        log.info("修改部门：{}",dept);
        deptService.putDept(dept);
        return Result.success();
    }


}
