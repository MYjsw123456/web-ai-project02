package com.lmy.service;

import com.lmy.pojo.Dept;

import java.util.List;

public interface DeptService {

     List<Dept> findAll();
     void deleteById(int id);

     void addDept(Dept dept);

     Dept getInfo(Integer id);

     void putDept(Dept dept);
}
