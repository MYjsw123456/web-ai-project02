package com.lmy.service.impl;

import com.lmy.mapper.EmpMapper;
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
}
