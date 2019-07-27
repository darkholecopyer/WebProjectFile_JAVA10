package com.osyunge.controller;

import com.osyunge.dataobject.FCResult;
import com.osyunge.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexImportController {
    @RequestMapping("/index/import")
    @ResponseBody
    public FCResult importIndex(){
        String json = HttpClientUtil.doGet("http://localhost:8083/search/import/all");
        if (!StringUtils.isBlank(json)){
            return FCResult.ok();
        }
        return null;
    }
}
