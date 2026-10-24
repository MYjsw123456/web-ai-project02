package com.lmy.mapper;

import com.lmy.pojo.Emp1;
import com.lmy.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    //-----------------------------------------------原始分页查询实现---------------------------------
/*    *//**
     *根据分页查询页码和分页数查询员工表
     *//*
    @Select("select e.*,d.name'deptName' from emp e left join dept d on e.dept_id=d.id order by e.update_time desc limit #{page},#{pageSize};")
    List<Emp1> getEmp(Integer page, Integer pageSize);

    *//**
     *查询员工总数
     *//*
    @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
    long getCount();*/

    /**
     * 查询员工信息
     */
//    @Select("select e.*,d.name'deptName' from emp e left join dept d on e.dept_id=d.id order by e.update_time desc")
    List<Emp1> getEmp(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp (username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp1 emp1);

    void deleteById(List<Integer> ids);

    @Select("select * from emp where id = #{id}")
    Emp1 getEmpById(Integer id);

    /**
     * 查询全部员工（仅 id、姓名，用于班主任下拉选项）
     */
    @Select("select id, name from emp")
    List<Emp1> findAll();

    /**
     * 统计指定部门下的员工数量
     */
    @Select("select count(*) from emp where dept_id = #{deptId}")
    Long countByDeptId(Integer deptId);

    void putEmp(Emp1 emp1);

    List<Map<String,Object>> countEmpJob();

    List<Map<String,Object>> countEmpGender();
}
