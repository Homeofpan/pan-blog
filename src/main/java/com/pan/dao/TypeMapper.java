package com.pan.dao;

import com.pan.pojo.Type;
import com.pan.pojo.TypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMapper {
    int countByExample(TypeExample example);

    int deleteByExample(TypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeExample example);

    Type selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByExample(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

    //自己添加的
    Type selectTypeForUpdate(Integer id);
    List<Type> selectTagByNumDesc(Type type);
}