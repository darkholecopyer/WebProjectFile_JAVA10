package com.osyunge.portal.controller;

import com.osyunge.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    @RequestMapping("/index")
    public String showIndex(Model model){
        //取大广告的内容
        String json = contentService.getAd1List();
        //传递给页面
        model.addAttribute("ad1",json);
        return "index";
    }
}