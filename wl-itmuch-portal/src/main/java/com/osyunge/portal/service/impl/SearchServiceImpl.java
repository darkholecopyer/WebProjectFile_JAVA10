package com.osyunge.portal.service.impl;

import com.osyunge.dataobject.FCResult;
import com.osyunge.dataobject.SearchResult;
import com.osyunge.portal.service.SearchService;
import com.osyunge.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, Integer page) {
        try{
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("q",queryString);
            paramMap.put("page",page+"");
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL,paramMap);
            FCResult result = FCResult.formatToPojo(json,SearchResult.class);
            if (result.getStatus()==200){
                SearchResult searchResult = (SearchResult)result.getData();
                return searchResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
