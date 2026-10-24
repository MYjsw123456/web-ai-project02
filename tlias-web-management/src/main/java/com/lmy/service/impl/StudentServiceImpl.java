package com.lmy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmy.mapper.StudentMapper;
import com.lmy.pojo.PageResult;
import com.lmy.pojo.Student;
import com.lmy.pojo.StudentQueryParam;
import com.lmy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 根据查询条件分页查询学员信息
     */
    @Override
    public PageResult<Student> getStudent(StudentQueryParam studentQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());

        //2.调用Mapper查询所有学员信息
        List<Student> students = studentMapper.getStudent(studentQueryParam);
        Page<Student> p = (Page<Student>) students;

        return new PageResult<>(p.getTotal(), students);

    }
    /**
     * 新增学员
     */
    @Override
    public void addStudent(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        student.setCreateTime(LocalDateTime.now());
        //新学员违纪次数、违纪扣分默认为0（前端不传，数据库非空约束要求不能为null）
        if (student.getViolationCount() == null) {
            student.setViolationCount((short) 0);
        }
        if (student.getViolationScore() == null) {
            student.setViolationScore((short) 0);
        }
        studentMapper.addStudent(student);
    }
    /**
     * 删除学员（支持批量删除）
     * @param ids 学员ID列表
     */
    @Override
    public void delete(List<Integer> ids) {
        studentMapper.delete(ids);
    }
    /**
     * 根据id查询学员
     * @param id 学员ID
     * @return 学员信息
     */
    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.getStudentById(id);
    }
    /**
     * 修改学员
     * @param student 学员信息
     */
    @Override
    public void updateStudent(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateStudent(student);
    }
    /**
     * 学员违纪处理
     */
    @Override
    public void violation(Integer id, Integer score) {
        studentMapper.violation(id, score);
    }
}
