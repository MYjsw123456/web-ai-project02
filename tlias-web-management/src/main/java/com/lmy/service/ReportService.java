package com.lmy.service;

import com.lmy.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {


    JobOption getEmpData();

    List<Map<String, Object>> getEmpGender();
}
