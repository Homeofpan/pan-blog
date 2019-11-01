package com.pan.controller;

import com.pan.pojo.Blog;
import com.pan.pojo.BlogByYear;
import com.pan.service.BlogService;
import org.apache.tomcat.util.modeler.modules.ModelerSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 *
 *
 * */
@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;


    //blogCount: 博客总数量
    //archiveMap{       list
    //      item{       每个年份一个list
    //          key     年份
    //          value{  数组 用来防止博客
    //              id
    //              title
    //              updatetime
    //              flag
    //          }
    //
    //      }
    // }
    @GetMapping("/archives")
    public String archives(Model model){
        List<BlogByYear> list = blogService.ArchivesBlogs();
        model.addAttribute("archiveMap",list);
        int i = blogService.CountBlogNum(new Blog());
        model.addAttribute("blogCount",i);
        return "archives";
    }


}
