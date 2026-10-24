package com.lmy.service.impl;

import com.lmy.mapper.EmpMapper;
import com.lmy.mapper.StudentMapper;
import com.lmy.pojo.ClazzOption;
import com.lmy.pojo.JobOption;
import com.lmy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    EmpMapper empMapper;

    @Autowired
    StudentMapper studentMapper;

    @Override
    public JobOption getEmpData() {

        List<Map<String, Object>> list = empMapper.countEmpJob();

        List<Object> jobList = list.stream().map(a -> a.get("pos")).toList();
        List<Object> dataList = list.stream().map(a -> a.get("num")).toList();

        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGender() {

        return empMapper.countEmpGender();
    }
    /**
     * 统计学员的学历信息
     */
    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegree();
    }

    /**
     * 统计每个班的人数
     */
    @Override
    public ClazzOption getStudentCountData() {

        List<Map<String, Object>> list = studentMapper.countStudentClazz();

        List<Object> clazzList = list.stream().map(a -> a.get("pos")).toList();
        List<Object> dataList = list.stream().map(a -> a.get("num")).toList();

        return new ClazzOption(clazzList,dataList);
    }
}
