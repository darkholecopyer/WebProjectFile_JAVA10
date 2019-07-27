package com.osyunge.portal.controller;

import com.osyunge.dataobject.SearchResult;
import com.osyunge.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model){
        SearchResult searchResult = null;
        try{
            if (queryString != null){
                queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
            }
            searchResult = searchService.search(queryString,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("page",page);
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("totalPages",searchResult.getPageCount());
        model.addAttribute("query",queryString);
        return "search";
    }
}
