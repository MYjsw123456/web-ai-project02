package com.lmy.mapper;

import com.lmy.pojo.Clazz;
import com.lmy.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClazzMapper {


    /**
     * 根据条件查询所有班级
     */
    List<Clazz> getClazz(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    @Insert("insert into clazz (name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "VALUES (#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void addClazz(Clazz clazz);

    /**
     * 根据id删除班级
     */
    @Delete("delete from clazz where id = #{id}")
    void deleteClazz(Integer id);

    /**
     * 根据id查询班级
     */
    @Select("select c.*, e.name as master_name from clazz c left join emp e on c.master_id = e.id where c.id = #{id}")
    Clazz getClazzById(Integer id);

    /**
     * 更新班级
     */
    void updateClazz(Clazz clazz);

    /**
     * 查询所有班级
     */
    @Select("select * from clazz")
    List<Clazz> getAllClazz();
}
