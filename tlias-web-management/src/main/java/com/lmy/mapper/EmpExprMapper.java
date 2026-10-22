package com.lmy.mapper;

import com.lmy.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpExprMapper {


    void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpId(List<Integer> ids);

    @Select("select * from emp_expr where emp_id = #{empId}")

    List<EmpExpr> getEmpExprById(Integer empId);

}
