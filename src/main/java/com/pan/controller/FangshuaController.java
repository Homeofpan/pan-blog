package com.pan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by pan tao on 2019/11/1
 **/
@Controller
public class FangshuaController {

    @GetMapping("/error/fangshua")
    public String tofangshua(){
        return "error/fangshua";
    }
}
