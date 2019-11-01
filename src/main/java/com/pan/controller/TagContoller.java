package com.pan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.pojo.Tag;
import com.pan.service.TagService;
import com.pan.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagContoller {

    @Autowired
    private TagService tagService;

    //跳转到tags页面
    @GetMapping("/tags")
    public String tags(Integer page, Model model){
        if (page == null || page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page, 10);
        List<Tag> tags = tagService.listTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);

        PageVo pageVo = new PageVo();
        pageVo.setContent(tags);
        pageVo.setFirst(pageInfo.isIsFirstPage());
        pageVo.setLast(pageInfo.isIsLastPage());
        pageVo.setTotalPages(pageInfo.getPages());
        pageVo.setNumber(pageInfo.getPageNum());

        model.addAttribute("page", pageVo);
        return "admin/tags";
    }

    //跳转到新增页面
    @GetMapping("/tags/input")
    public String toInputPage(Model model){
        Tag tag = new Tag();
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    //新增tag
    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes){
        if(StringUtils.isEmpty(tag.getName())){
            attributes.addFlashAttribute("message", "操作失败,标签名不能为空");
            return "redirect:/admin/tags";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","操作失败,该分类已经存在");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    //跳转到修改页面
    @GetMapping("/tags/{id}/input")
    public String toModifyPage(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes){
        Tag tag = tagService.getTagById(id);
        if(tag != null){
            model.addAttribute("tag", tag);
            return "admin/tags-input";
        }
        attributes.addFlashAttribute("message", "该标签已经被删除,请刷新页面");
        return "redirect:/admin/tags";
    }

    //修改tag
    @PostMapping("/tags/{id}")
    public String modify(Model model,@PathVariable("id") Integer id,String name, RedirectAttributes attributes){
        if(id == null || id == 0) {
            attributes.addFlashAttribute("message","修改失败,请重试");
            return "redirect:/admin/tags";
        }
        if(StringUtils.isEmpty(name)){
            attributes.addFlashAttribute("message","修改失败,标签不能为空");
            return "redirect:/admin/tags";
        }
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        Tag tag1 = tagService.updateTag(tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message","修改失败,请重试");
            return "redirect:/admin/tags";
        }
        attributes.addFlashAttribute("message","修改成功");
        return "redirect:/admin/tags";
    }

    //删除tag
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        if (id == null || id == 0){
            attributes.addFlashAttribute("message","删除失败,请刷新后重试");
            return "redirect:/admin/tags";
        }
        tagService.deleteTagById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
