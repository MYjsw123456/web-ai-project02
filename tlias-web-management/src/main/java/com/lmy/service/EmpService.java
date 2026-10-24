package com.lmy.service;

import com.lmy.pojo.Emp1;
import com.lmy.pojo.EmpQueryParam;
import com.lmy.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageResult<Emp1> getEmp(EmpQueryParam empQueryParam);

    void save(Emp1 emp1);

    void delete(List<Integer> ids);

    Emp1 getEmpById(Integer id);

    void putEmp(Emp1 emp1);

    /**
     * 查询全部员工（班主任下拉选项）
     */
    List<Emp1> findAll();
}
