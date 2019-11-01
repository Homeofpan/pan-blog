package com.pan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.annotation.AccessLimit;
import com.pan.pojo.Blog;
import com.pan.pojo.Type;
import com.pan.pojo.User;
import com.pan.service.BlogService;
import com.pan.service.SolrService;
import com.pan.service.TagService;
import com.pan.service.TypeService;
import com.pan.vo.PageVo;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SolrService solrService;

    //第一次访问的时候,blog[title:"",recommend:""],typeId:null,page=null
//    @AccessLimit(seconds = 3,maxCount = 2,needLogin = true)
    @GetMapping("/blogs")
    public String blogsPage(Blog blog,Integer typeId,Integer page, Model model){
        ListBolgData(typeId, page, model, blog);
        return LIST;
    }

    //换页+搜索
    @PostMapping("/blogs/search")
    public String blogSearch(Boolean recommend,String title,Integer typeId,Integer page, Model model){
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setTypeid(typeId);
        blog.setRecommend(recommend?1:0);

        ListBolgData(typeId, page, model, blog);
        return "admin/blogs :: blogList";
    }

    //实现上述的两个功能
    private void ListBolgData(Integer typeId, Integer page, Model model, Blog blog) {
        if(page == null || page <= 0){
            page = 1;
        }
        Type type = null;
        if(typeId != null){
            blog.setTypeid(typeId);
            //获取类型
            type = typeService.getTypeById(typeId);
        }

        //查询博客:如果是第一次访问 会查询出所有的博客
        PageHelper.startPage(page, 10);
        List<Blog> blogs = blogService.listBlogInManager(blog);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);


        //遍历查询出的博客进行处理
        for (Blog b : blogs){

            if(type != null){
                b.setType(type);
            }else {
                b.setType(typeService.getTypeById(b.getTypeid()));
            }
        }

        PageVo pageVo = new PageVo();
        pageVo.setContent(blogs);
        pageVo.setFirst(pageInfo.isIsFirstPage());
        pageVo.setLast(pageInfo.isIsLastPage());
        pageVo.setNumber(pageInfo.getPageNum());
        pageVo.setTotalPages(pageInfo.getPages());

        model.addAttribute("page",pageVo);

        model.addAttribute("types",typeService.typeList());
    }

    //跳转到新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.typeList());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    //新增博客 + 修改博客
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        //封装数据
        User user = (User) session.getAttribute("user");
        blog.setUid(user.getId());
        blog.setTypeid(blog.getType().getId());
        blog.setViews(0);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        if(StringUtils.isEmpty(blog.getFlag())){
            blog.setFlag("原创");
        }
        Blog blog1 = null;
        //由于新增和修改使用同一个页面,所以要判断是新增还是修改
        if(blog.getId() == null){   //新增:新增博客,修改type的num,修改tag的num
            blog1 = blogService.saveBolg(blog);
            typeService.increaseBlogsNum(blog.getTypeid());
            String[] split = blog.getTagids().split(",");
            //遍历增加tag的blogs
            for (String tagid : split){
                Integer i = Integer.parseInt(tagid);
                tagService.increaseBlogsNum(i);
            }

        }else{  //修改:修改博客,修改type的num,修改tag的num
            //根据id查询出博客 获取出他的typeid和tagids;
            //比较需要更新的typeid和tagids,做出相应的修改
            Blog oldBlog = blogService.getBolg(blog.getId());
            if (oldBlog.getTypeid() != blog.getTypeid()) {
                typeService.decreaseBlogNum(oldBlog.getTypeid());
                typeService.increaseBlogsNum(blog.getTypeid());
            }
            String oldTagids = oldBlog.getTagids();
            String newTagids = blog.getTagids();

            String[] oldTagidsList = oldTagids.split(",");
            for (String oldTagid : oldTagidsList) { //遍历若久的tagid不存在与新的tagid则-1
                if (!newTagids.contains(oldTagid)) {
                    tagService.decreaseBlogNum(Integer.parseInt(oldTagid));
                }
            }
            String[] newTagidsList = newTagids.split(",");
            for (String newTagid : newTagidsList) {
                if (!oldTagids.contains(newTagid)) {
                    tagService.increaseBlogsNum(Integer.parseInt(newTagid));
                }
            }
            blog1 = blogService.updateBolg(blog.getId(), blog);
        }
        if(blog1 == null){
            attributes.addFlashAttribute("message","保存失败");
        }
        if(blog.getPublished() == 0){
            attributes.addFlashAttribute("message","保存成功");
        }else {
            attributes.addFlashAttribute("message","发布成功");
        }
        //操作数据库成功,修改solr的数据
        solrService.saveBlogToSolr(blog1);
        return REDIRECT_LIST;
    }

    //跳转到修改页面:注意要携带blog回去的时候必须要手动给blog赋值Type
    @GetMapping("/blogs/{id}/input")
    public String toModifyPage(@PathVariable Integer id,Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.typeList());
        Blog blog = blogService.getBolg(id);
        blog.setType(typeService.getTypeById(blog.getTypeid()));
        model.addAttribute("blog",blog);
        return INPUT;
    }

    //删除博客:博客+type数量+tag数量
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Integer id,RedirectAttributes attributes){
        Blog blog = blogService.getBolg(id);
        typeService.decreaseBlogNum(blog.getTypeid());
        String[] split = blog.getTagids().split(",");
        //遍历增加tag的blogs
        for (String tagid : split){
            Integer i = Integer.parseInt(tagid);
            tagService.decreaseBlogNum(i);
        }
        blogService.deleteBlog(id);
        solrService.deleteBlog(id);
        attributes.addAttribute("message","删除成功");
        return REDIRECT_LIST;
    }


}
