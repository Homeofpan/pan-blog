package com.pan.dao;

import java.util.List;

import com.pan.pojo.Blog;
import com.pan.pojo.BlogExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogMapper {
    int countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectByExampleWithBLOBs(BlogExample example);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExampleWithBLOBs(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);
    
    //自己添加的
    List<Blog> selectByTitleLike(Blog blog);
    List<Blog> selectBlogByTimeDesc(Blog blog);
    List<Blog> selectRecommendBlogsOrderByViews();
    List<String> selectAllYears();
    List<Blog> selectBolyGroupByYear(String year);
}