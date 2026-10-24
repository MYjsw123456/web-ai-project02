package com.lmy.mapper;

import com.lmy.pojo.Student;
import com.lmy.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    /**
     * 根据查询条件分页查询学员信息
     */
    List<Student> getStudent(StudentQueryParam studentQueryParam);

    /**
     * 新增学员
     */
    @Insert("INSERT INTO student (name,no, gender, phone,id_card, address,degree,graduation_date,clazz_id,is_college,violation_count,violation_score, create_time, update_time) " +
            "VALUES (#{name}, #{no}, #{gender}, #{phone}, #{idCard}, #{address}, #{degree}, #{graduationDate}, #{clazzId}, #{isCollege}, #{violationCount}, #{violationScore}, #{createTime}, #{updateTime})")
    void addStudent(Student student);

    /**
     * 删除学员（支持批量删除）
     */
    void delete(@Param("ids") List<Integer> ids);

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student getStudentById(Integer id);

    void updateStudent(Student student);

    @Update("UPDATE student SET violation_count = violation_count + 1, violation_score = violation_score + #{score} WHERE id = #{id}")
    void violation(Integer id, Integer score);

    /**
     * 统计学员的学历信息
     */
    List<Map<String, Object>> countStudentDegree();

    /**
     * 统计每个班的人数
     */
    List<Map<String, Object>> countStudentClazz();

    /**
     * 根据班级id统计人数
     */
    @Select("SELECT COUNT(*) FROM student WHERE clazz_id = #{id}")
    int countStudentByClazzId(Integer id);
}
