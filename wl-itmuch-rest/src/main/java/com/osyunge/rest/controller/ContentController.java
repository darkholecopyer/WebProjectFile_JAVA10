package com.osyunge.rest.controller;

import com.osyunge.dataobject.FCResult;
import com.osyunge.pojo.TbContent;
import com.osyunge.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/content/{cid}")
    @ResponseBody
    public FCResult getContentList(@PathVariable Long cid){
        try {
            List<TbContent> contentList = contentService.getContentList(cid);
            return FCResult.ok(contentList);
        }catch (Exception e){
            e.printStackTrace();
            return FCResult.build(500,"服务器内部异常");
        }
    }
}
