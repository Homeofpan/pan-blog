package com.pan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.pojo.Blog;
import com.pan.pojo.Type;
import com.pan.service.BlogService;
import com.pan.service.TypeService;
import com.pan.service.UserService;
import com.pan.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    private static final Integer BLOG_PAGE_SIZE = 7;

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @GetMapping("/types/{id}")
    public String typesPage(Integer page,@PathVariable Integer id, Model model){
        if(page == null || page <= 0){
            page = 1;
        }
        Blog blog = new Blog();
        if(id >= 1){
            blog.setTypeid(id);
        }
        //按条件,时间倒序查询博客()若条件为空则默认查询全部
        PageHelper.startPage(page,BLOG_PAGE_SIZE);
        List<Blog> blogs = blogService.listBlogByTimeDesc(blog);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

        for(Blog b : blogs){
            b.setUser(userService.findUserById(b.getUid()));
            b.setType(typeService.getTypeById(b.getTypeid()));
        }

        PageVo pageVo = new PageVo();
        pageVo.setContent(blogs);
        pageVo.setTotalPages(pageInfo.getPages());
        pageVo.setNumber(pageInfo.getPageNum());
        pageVo.setFirst(pageInfo.isIsFirstPage());
        pageVo.setLast(pageInfo.isIsLastPage());
        pageVo.setTotalElements(pageInfo.getTotal());
        model.addAttribute("page",pageVo);

        //获取全部类型用于展示,按照num排序
        List<Type> types = typeService.listTypeByNumDesc(new Type());

        model.addAttribute("types", types);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
