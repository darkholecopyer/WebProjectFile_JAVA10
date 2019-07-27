package com.osyunge.search.controller;

import com.osyunge.dataobject.FCResult;
import com.osyunge.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/import/all")
    @ResponseBody
    public FCResult importAllItemInfo(){
        FCResult itemListResult = itemService.getItemList();
        return itemListResult;
    }
}
