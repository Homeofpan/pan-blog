package com.pan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.exception.NotFoundException;
import com.pan.pojo.Blog;
import com.pan.pojo.Tag;
import com.pan.pojo.Type;
import com.pan.pojo.User;
import com.pan.service.*;
import com.pan.util.MarkdownUtils;
import com.pan.vo.PageVo;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {

    private static final Integer BLOG_PAGE_SIZE = 7;
    private static final Integer TAG_PAGE_SIZE = 10;
    private static final Integer TYPE_PAGE_SIZE = 6;
    private static final Integer RCCOMMEND_PAGE_SIZE = 6;

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private SolrService solrService;

    //访问首页面
    @GetMapping("/")
    public String index(Integer page, Model model) {
        if (page == null || page <= 0) {
            page = 0;   //用数据库page要设置为1,用solr设置为0
        }

        //按照时间倒序查询博客
        QueryResponse response = solrService.getQueryResult(page, null);
        //将查询结果包装为PageVo
        PageVo pageVo = solrService.warpResponseToPageVo(page, response);
        model.addAttribute("page", pageVo);

        //按照类型对应博客数查询类型
        PageHelper.startPage(1, TYPE_PAGE_SIZE);
        List<Type> types = typeService.listTypeByNumDesc(new Type());
        model.addAttribute("types", types);

        //按照标签对应博客数查询类型
        PageHelper.startPage(1, TAG_PAGE_SIZE);
        List<Tag> tags = tagService.listTagByNumDesc(new Tag());
        model.addAttribute("tags", tags);

        //按照推荐查询博客
        PageHelper.startPage(1, RCCOMMEND_PAGE_SIZE);
        List<Blog> recommendBlogs = blogService.listRecommendBlogs();
        model.addAttribute("recommendBlogs", recommendBlogs);
        return "index";
    }

    //查找博客
    @PostMapping("/search")
    public String searche(Integer page, String query, Model model) {
        if (page == null || page <= 0) {   //用数据库page要设置为1,用solr设置为0
            page = 0;
        }
        //获取查询返回的response
        QueryResponse response = solrService.getQueryResult(page, query);
        //将response包装为PageVo
        PageVo pageVo = solrService.warpResponseToPageVo(page, response);

        model.addAttribute("page", pageVo);
        return "search";

}

    //访问具体博客
    @GetMapping("/blog/{id}")
        public String blog(@PathVariable Integer id, Model model) {
        Blog blog = blogService.getBolg(id);
        if (blog == null) {
            throw new NotFoundException("资源没找到");
        }
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        blog.setType(typeService.getTypeById(blog.getTypeid()));
        blog.setUser(userService.findUserById(blog.getUid()));
        blog.setTags(tagService.listTagByTagIds(blog.getTagids()));
        model.addAttribute("blog", blog);
        return "blog";
    }

    //访问关于我页面
    @GetMapping("/about")
    public String about() {
        return "about";
    }



}
