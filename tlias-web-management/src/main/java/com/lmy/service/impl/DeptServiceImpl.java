package com.lmy.service.impl;

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

    public List<Dept> findAll(){
        return mapperDept.findAll();
    }

    @Override
    public void deleteById(int id) {
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
