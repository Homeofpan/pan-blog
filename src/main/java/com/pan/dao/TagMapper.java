package com.pan.dao;

import com.pan.pojo.Tag;
import com.pan.pojo.TagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapper {
    int countByExample(TagExample example);

    int deleteByExample(TagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    List<Tag> selectByExample(TagExample example);

    Tag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tag record, @Param("example") TagExample example);

    int updateByExample(@Param("record") Tag record, @Param("example") TagExample example);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    Tag selectTagForUpdate(Integer tagid);

    List<Tag> selectTagByNumDesc(Tag tag);
}