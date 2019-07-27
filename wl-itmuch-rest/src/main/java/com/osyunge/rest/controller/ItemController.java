package com.osyunge.rest.controller;

import com.osyunge.dataobject.FCResult;
import com.osyunge.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public FCResult getItemInfo(@PathVariable long itemId){
        FCResult result = itemService.getItemBaseInfo(itemId);
        return result;
    }
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public FCResult getItemDesc(@PathVariable Long itemId){
        FCResult result = itemService.getItemDesc(itemId);
        return result;
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public FCResult getItemParam(@PathVariable Long itemId){
        FCResult result = itemService.getItemParam(itemId);
        return result;
    }
}
