package com.pan.service.impl;

import com.pan.dao.BlogMapper;
import com.pan.exception.NotFoundException;
import com.pan.pojo.Blog;
import com.pan.pojo.BlogByYear;
import com.pan.pojo.BlogExample;
import com.pan.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public Blog getBolg(Integer id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        return blog;
    }


    @Transactional
    @Override
    public List<Blog> listBlogInManager(Blog blog) {
        List<Blog> blogs = blogMapper.selectByTitleLike(blog);
        return blogs;
    }

    @Transactional
    @Override
    public List<Blog> listBolg(Blog blog) {
        //TODO
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        if (blog.getTitle() != null){
            criteria.andTitleEqualTo(blog.getTitle());
        }
        if(blog.getTypeid() != null){
            criteria.andTypeidEqualTo(blog.getTypeid());
        }
        if(blog.getRecommend() == 1){
            criteria.andRecommendEqualTo(blog.getRecommend());
        }

        List<Blog> blogs = blogMapper.selectByExample(example);
        return null;

        //criteria.andIdEqualTo(blog.getId());
        //criteria.andUidEqualTo(blog.getUid());
        //criteria.andTypeidEqualTo(blog.getTypeid());
        //criteria.andTagidsEqualTo(blog.getTagids());
        //criteria.andTitleEqualTo(blog.getTitle());
        //criteria.andFirstPictureEqualTo(blog.getFirstPicture());
        //criteria.andViewsEqualTo(blog.getViews());
        //criteria.andAppreciationEqualTo(blog.getAppreciation());
        //criteria.andShareStatementEqualTo(blog.getShareStatement());
        //criteria.andCommentabledEqualTo(blog.getCommentabled());
        //criteria.andPublishedEqualTo(blog.getPublished());
        //criteria.andRecommendEqualTo(blog.getRecommend());
        //criteria.andCreateTimeEqualTo(blog.getCreateTime());
        //criteria.andUpdateTimeEqualTo(blog.getUpdateTime());
    }

    @Override
    public List<Blog> listBlogByTimeDesc(Blog blog) {
        List<Blog> blogs = blogMapper.selectBlogByTimeDesc(blog);
        return blogs;
    }

    @Transactional
    @Override
    public Blog saveBolg(Blog blog) {
        blogMapper.insertSelective(blog);
        return blog;
    }

    @Transactional
    @Override
    public Blog updateBolg(Integer id, Blog blog) {
        Blog blog1 = getBolg(id);
        if(blog != null){
            blogMapper.updateByPrimaryKeySelective(blog);
            return blog;
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteBlog(Integer id) {
        blogMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public List<Blog> listRecommendBlogs() {
        return blogMapper.selectRecommendBlogsOrderByViews();
    }

    @Transactional
    @Override
    public List<BlogByYear> ArchivesBlogs() {
        //以降序地形式,从数据库中查询出博客的年份
        List<String> years = blogMapper.selectAllYears();
        List<BlogByYear> list = new ArrayList<>();
        for(String year : years){
            //查询出对应年份的所有博客
            List<Blog> listBlogByYear = blogMapper.selectBolyGroupByYear(year);
            BlogByYear blogByYear = new BlogByYear();
            blogByYear.setKey(year);
            blogByYear.setValue(listBlogByYear);
            list.add(blogByYear);
        }
        return list;
    }

    @Override
    public int CountBlogNum(Blog blog) {
        BlogExample example = new BlogExample();
        example.createCriteria();
        int i = blogMapper.countByExample(example);
        return i;
    }

    @Transactional
    @Override
    public Integer incrBlogViews(Blog blog) {
        if (blog == null) {
            throw new  NotFoundException("资源没找到");
        }
        //获取当前访问的博客的访问量
        Integer views = blog.getViews();
        //加一操作
        Integer newViews = ++views;
        blog.setViews(newViews);
        blogMapper.updateByPrimaryKeySelective(blog);
        return newViews;
    }

}
