package com.osyunge.portal.controller;

import com.osyunge.pojo.TbItem;
import com.osyunge.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        TbItem item = itemService.getItemById(itemId);
        model.addAttribute("item",item);
        return "item";
    }
    @RequestMapping("/item/desc/{itemId}")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId){
        String s = itemService.getItemDescById(itemId);
        return s;
    }
    @RequestMapping("/item/param/{itemId}")
    @ResponseBody
    public String getItemParam(@PathVariable Long itemId){
        String s = itemService.getItemParam(itemId);
        return s;
    }
}
