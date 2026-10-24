package com.lmy.service;

import com.lmy.pojo.Clazz;
import com.lmy.pojo.ClazzQueryParam;
import com.lmy.pojo.PageResult;

import java.util.List;

public interface ClazzService {

    /**
     * 根据条件查询所有班级
     */
    PageResult<Clazz> getClazz(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    void addClazz(Clazz clazz);

    /**
     * 根据id删除班级
     */
    void deleteClazz(Integer id);

    /**
     * 根据id查询班级
     */
    Clazz getClazzById(Integer id);

    /**
     * 更新班级
     */
    void updateClazz(Clazz clazz);

    /**
     * 查询所有班级
     */
    List<Clazz> getAllClazz();

}
