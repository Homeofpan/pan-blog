package com.pan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.pojo.Type;
import com.pan.service.TypeService;
import com.pan.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //跳转到types页面
    @GetMapping("/types")
    public String types(Integer page, Model model) {
        if (page == null || page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page, 10);
        List<Type> types = typeService.typeList();
        PageInfo<Type> pageInfo = new PageInfo<>(types);

        PageVo pageVo = new PageVo();
        pageVo.setContent(types);
        pageVo.setFirst(pageInfo.isIsFirstPage());
        pageVo.setLast(pageInfo.isIsLastPage());
        pageVo.setTotalPages(pageInfo.getPages());
        pageVo.setNumber(pageInfo.getPageNum());

        model.addAttribute("page", pageVo);
        return "admin/types";
    }

    //跳转到新增页面
    @GetMapping("/types/input")
    public String input(Model model) {
        Type type = new Type();
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    //新增type
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes) {
        if (StringUtils.isEmpty(type.getName())) {
            attributes.addFlashAttribute("message", "操作失败,分类名不能为空");
            return "redirect:/admin/types";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "操作失败,该分类已经存在");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    //跳转到修改页面
    @GetMapping("/types/{id}/input")
    public String toModifyPage(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Type type = typeService.getTypeById(id);
        if (type != null) {
            model.addAttribute("type", type);
            return "admin/types-input";
        }
        attributes.addFlashAttribute("message", "该选项已经被删除,请刷新页面");
        return "redirect:/admin/types";

    }

    //修改type
    @PostMapping("/types/{id}")
    public String modify(Model model,@PathVariable("id") Integer id,String name, RedirectAttributes attributes){
        System.out.println(id);
        if(id == null || id == 0) {
            attributes.addFlashAttribute("message","修改失败,请重试");
            return "redirect:/admin/types";
        }
        if(StringUtils.isEmpty(name)){
            attributes.addFlashAttribute("message","修改失败,类型不能为空");
            return "redirect:/admin/types";
        }
        Type type = new Type();
        type.setId(id);
        type.setName(name);

        Type type1 = typeService.updateType(type);
        if(type1 == null){
            attributes.addFlashAttribute("message","修改失败,分类已存在");
            return "redirect:/admin/types";
        }
        attributes.addFlashAttribute("message","修改成功");
        return "redirect:/admin/types";
    }

    //删除type
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
        if (id == null || id == 0){
            attributes.addFlashAttribute("message","删除失败,请刷新后重试");
            return "redirect:/admin/types";
        }
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
