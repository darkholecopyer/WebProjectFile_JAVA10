package com.osyunge.controller;

import com.osyunge.dataobject.EasyUITreeNode;
import com.osyunge.dataobject.FCResult;
import com.osyunge.service.ItemContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 */
@Controller
@RequestMapping("/content/category")
public class ItemContentCategoryController {
    @Autowired
    private ItemContentCategoryService contentCategoryService;
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        return contentCategoryService.getContentCategoryList(parentId);
    }
    @RequestMapping("/create")
    @ResponseBody
    public FCResult createNode(Long parentId, String name){
        FCResult result = contentCategoryService.insertCategory(parentId,name);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public FCResult updateNode(Long id, String name){
        FCResult result = contentCategoryService.updateCategory(id,name);
        return result;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public FCResult deleteNode(long parentId,long id){
        FCResult result = contentCategoryService.deleteCategory(parentId,id);
        return result;
    }
}
