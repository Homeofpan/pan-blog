package com.pan.service;

import com.pan.pojo.Blog;
import com.pan.pojo.BlogByYear;

import java.util.List;

public interface BlogService {

    //根据id查找blog,直接返回查找结果,自己判断是否为null
    Blog getBolg(Integer id);

    //后台列表展示,按推荐,标题(模糊),类型查找
    public List<Blog> listBlogInManager(Blog blog);

    //TODO 查找全部博客
    List<Blog> listBolg(Blog blog);

    //按时间倒序查询博客,直接返回数组,需自己判断是否size为0
    List<Blog> listBlogByTimeDesc(Blog blog);

    //新增博客,返回null表示失败
    Blog saveBolg(Blog blog);

    //更新博客,返回null表示失败
    Blog updateBolg(Integer id, Blog blog);

    //删除博客,默认成功
    void deleteBlog(Integer id);

    //获取推荐的博客
    List<Blog> listRecommendBlogs();

    //归档博客,返回一个数组,数组里有多个数组(每一年一个数组)
    List<BlogByYear> ArchivesBlogs();

    //获取总的博客记录数
    int CountBlogNum(Blog blog);



}
