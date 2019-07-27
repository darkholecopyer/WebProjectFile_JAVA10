package com.osyunge.rest.controller;

import com.osyunge.dataobject.ItemCatResult;
import com.osyunge.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/all")
    @ResponseBody
    public Object getItemCatList(String callback){
        ItemCatResult result = itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)){
            //需要把result转换成字符串
            return result;
        }
        //如果字符串不为空，需要支持jsonp调用
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
