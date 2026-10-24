package com.lmy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmy.exception.BusinessException;
import com.lmy.mapper.ClazzMapper;
import com.lmy.mapper.StudentMapper;
import com.lmy.pojo.Clazz;
import com.lmy.pojo.ClazzQueryParam;
import com.lmy.pojo.PageResult;
import com.lmy.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService{

    @Autowired
    ClazzMapper clazzMapper;

    @Autowired
    StudentMapper studentMapper;
    /**
     * 根据条件查询所有班级
     */
    @Override
    public PageResult<Clazz> getClazz(ClazzQueryParam clazzQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        //2.根据条件调用Mapper查询所有班级
        List<Clazz> clazz = clazzMapper.getClazz(clazzQueryParam);
        Page<Clazz> page = (Page<Clazz>) clazz;


        return new PageResult<>(page.getTotal(),page.getResult());
    }
    /**
     * 添加班级
     */
    @Override
    public void addClazz(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.addClazz(clazz);
    }
    /**
     * 根据id删除班级
     */
    @Override
    public void deleteClazz(Integer id) {
        //1.检验当前班级下是否有学生
        if (studentMapper.countStudentByClazzId(id) > 0) {
            throw new BusinessException("当前班级下有学生，不能删除！");
        }

        clazzMapper.deleteClazz(id);
    }
    /**
     * 根据id查询班级
     */
    @Override
    public Clazz getClazzById(Integer id) {

        Clazz clazz = clazzMapper.getClazzById(id);
        return clazz;
    }
    /**
     * 更新班级
     */
    @Override
    public void updateClazz(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateClazz(clazz);
    }

    /**
     * 查询所有班级
     */
    @Override
    public List<Clazz> getAllClazz() {
        return clazzMapper.getAllClazz();
    }
}
