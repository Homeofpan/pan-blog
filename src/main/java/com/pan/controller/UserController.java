package com.pan.controller;

import com.pan.pojo.User;
import com.pan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    //访问登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    //登录
    @PostMapping("/login")
    public ModelAndView userLogin(String username, String password, ModelAndView mv, HttpSession session) {
        //判断字符串是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(username)) {
            mv.addObject("message", "账户或密码不能为空");
            mv.setViewName("admin/login");
            return mv;
        }

        User user = userService.findUserByUsername(username);
        //查找到对应用户,判断密码
        if (null != user) {
            if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
                //登陆成功
                session.setAttribute("user", user);
                mv.setViewName("redirect:/admin/index");
                return mv;
            }
            mv.addObject("message", "密码错误");
            mv.setViewName("admin/login");
            return mv;
        }

        mv.addObject("message", "用户不存在");
        mv.setViewName("admin/login");
        return mv;
    }

    //注销
    @GetMapping("/logout")
    public ModelAndView userLogout(HttpSession session, ModelAndView mv) {
        session.removeAttribute("user");
        mv.setViewName("redirect:login");
        return mv;
    }
}
