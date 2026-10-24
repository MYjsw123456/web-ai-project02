package com.lmy.service.impl;

import com.lmy.exception.BusinessException;
import com.lmy.mapper.EmpMapper;
import com.lmy.mapper.MapperDept;
import com.lmy.pojo.Dept;
import com.lmy.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {


    @Autowired
    MapperDept mapperDept;

    @Autowired
    private EmpMapper empMapper;

    public List<Dept> findAll(){
        return mapperDept.findAll();
    }

    @Override
    public void deleteById(int id) {
        //1.校验当前部门下是否存在员工
        Long empCount = empMapper.countByDeptId(id);
        if (empCount != null && empCount > 0) {
            //2.存在员工则不允许删除，抛出业务异常，由全局异常处理器返回错误提示
            throw new BusinessException("对不起，当前部门下有员工，不能直接删除！");
        }

        //3.无员工才执行删除
        mapperDept.deleteById(id);
    }

    @Override
    public void addDept(Dept dept) {
        //获取当前时间 年月日时分秒
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        mapperDept.addDept(dept);
    }

    @Override
    public Dept getInfo(Integer id) {
        Dept dept = mapperDept.getInfo(id);
        return dept;
    }

    @Override
    public void putDept(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        mapperDept.putDept(dept);
    }
}
