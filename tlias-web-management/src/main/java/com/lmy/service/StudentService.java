package com.lmy.service;

import com.lmy.pojo.PageResult;
import com.lmy.pojo.Student;
import com.lmy.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    /**
     * 根据查询条件分页查询学员信息
     * @param studentQueryParam 查询条件
     * @return 学员信息
     */
    PageResult<Student> getStudent(StudentQueryParam studentQueryParam);

    /**
     * 新增学员
     * @param student 学员信息
     */
    void addStudent(Student student);

    /**
     * 删除学员（支持批量删除）
     * @param ids 学员ID列表
     */
    void delete(List<Integer> ids);

    /**
     * 根据id查询学员
     * @param id 学员ID
     * @return 学员信息
     */
    Student getStudentById(Integer id);

    /**
     * 修改学员
     * @param student 学员信息
     */
    void updateStudent(Student student);

    /**
     * 学员违纪处理
     * @param id 学员ID
     * @param score 违纪分数
     */
    void violation(Integer id, Integer score);
}
