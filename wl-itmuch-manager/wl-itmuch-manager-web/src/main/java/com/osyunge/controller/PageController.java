package com.osyunge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示页面
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
