package com.lmy.controller;

import com.lmy.pojo.ClazzOption;
import com.lmy.pojo.JobOption;
import com.lmy.pojo.Result;
import com.lmy.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/empJobData")
    public Result getEmpData(){

        log.info("统计职位人数");
        JobOption empData = reportService.getEmpData();

        return Result.success(empData);
    }

    @GetMapping("/empGenderData")
    public Result getEmpGender(){

        log.info("统计性别信息");
        List<Map<String,Object>> list = reportService.getEmpGender();

        return Result.success(list);

    }
    /**
     * 统计学员的学历信息
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){

        log.info("统计学员的学历信息");
        List<Map<String,Object>> list = reportService.getStudentDegreeData();

        return Result.success(list);

    }

    /**
     * 统计每个班的人数
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData(){

        log.info("统计每个班的人数");
        ClazzOption clazzOption = reportService.getStudentCountData();

        return Result.success(clazzOption);

    }

}
