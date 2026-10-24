package com.lmy.service;

import com.lmy.pojo.ClazzOption;
import com.lmy.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {


    JobOption getEmpData();

    List<Map<String, Object>> getEmpGender();

    /**
     * 统计学员的学历信息
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计每个班的人数
     */
    ClazzOption getStudentCountData();
}
