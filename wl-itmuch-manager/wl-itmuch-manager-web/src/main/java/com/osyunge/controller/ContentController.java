package com.osyunge.controller;

import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.dataobject.FCResult;
import com.osyunge.pojo.TbContent;
import com.osyunge.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 内容管理
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDataGridResult queryList(long categoryId, int page, int rows){
        return contentService.queryList(categoryId,page,rows);
    }
    @RequestMapping("/save")
    @ResponseBody
    public FCResult saveContent(TbContent content){
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        contentService.saveContent(content);
        return FCResult.ok(200);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public FCResult deleteContent(long[] ids){
        for (long id:ids) {
            contentService.deleteContent(id);
        }
        return FCResult.ok(ids);
    }
    @RequestMapping("/edit")
    @ResponseBody
    public FCResult editContent(TbContent content){
        Date date = new Date();
        content.setUpdated(date);
        contentService.editContent(content);
        return FCResult.ok(200);
    }
}
