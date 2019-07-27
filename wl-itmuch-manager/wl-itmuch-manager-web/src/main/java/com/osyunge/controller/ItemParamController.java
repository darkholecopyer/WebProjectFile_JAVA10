package com.osyunge.controller;

import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.dataobject.FCResult;
import com.osyunge.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult queryAllList(int page,int rows){
        return itemParamService.selectItemParamList(page,rows);
    }

    @RequestMapping("/query/itemcatid/{itemcatid}")
    @ResponseBody
    public FCResult queryItemCatId(@PathVariable long itemcatid){
        return itemParamService.queryItemCatId(itemcatid);
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public FCResult insertItemParam(@PathVariable Long cid,String paramData){
        System.err.println("paramData:"+paramData);
        return itemParamService.insertItemParam(cid,paramData);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public FCResult delete(long[] ids){
        boolean flag = true;
        for (int i = 0; i < ids.length; i++) {
            int count = itemParamService.deleteItemParamById(ids[i]);
            if (count!=1){
                flag = false;
            }
        }
        FCResult result = new FCResult();
        if (flag){
            result.setStatus(200);
        }else {
            result.setStatus(201);
        }
        return result;
    }
}

