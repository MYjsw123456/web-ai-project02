package com.lmy.mapper;

import com.lmy.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface MapperDept {

    //方式1：手动结果映射
/*    @Results({
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime")
    })*/

    //方式2：起别名
    @Select("select id, name, create_time, update_time from dept order by update_time desc ")
    List<Dept> findAll();

    @Delete("delete from tlias.dept where id=#{id}")
    void deleteById(int id);

    @Insert("insert into dept ( name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    void addDept(Dept dept);

    @Select("select * from dept where id=#{id}")
    Dept getInfo(Integer id);

    @Update("update  dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void putDept(Dept dept);
}
