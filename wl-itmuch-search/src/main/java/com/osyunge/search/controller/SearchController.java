package com.osyunge.search.controller;

import com.osyunge.dataobject.FCResult;
import com.osyunge.dataobject.SearchResult;
import com.osyunge.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/query")
    @ResponseBody
    public FCResult search(@RequestParam("q") String queryString,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "60") Integer rows) {
        //查询条件不能为空
        if (StringUtils.isBlank(queryString)) {
            return FCResult.build(400, "查询条件不能为空");
        }
        SearchResult searchResult = null;
        try {
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            searchResult = searchService.search(queryString, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            return FCResult.build(500, "服务器内部异常");
        }
        return FCResult.ok(searchResult);
    }
}

