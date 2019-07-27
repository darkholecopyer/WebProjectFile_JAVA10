package com.osyunge.portal.service.impl;

import com.osyunge.dataobject.FCResult;
import com.osyunge.pojo.TbContent;
import com.osyunge.portal.pojo.AdNode;
import com.osyunge.portal.service.ContentService;
import com.osyunge.utils.HttpClientUtil;
import com.osyunge.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 胡金才
 * 2019/6/18 9:34
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;

    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;

    @Override
    public String getAd1List() {
//        String url = REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID;
        // 调用rest服务内容管理接口
        String url = "http://localhost:8081/rest/content/89";
        String json = HttpClientUtil.doGet(url);
        FCResult fcResult = FCResult.formatToList(json, TbContent.class);
        List<TbContent> contentList = (List<TbContent>) fcResult.getData();
        // 转换List<AdNode>
        List<AdNode> nodeList = new ArrayList<>();
        for (TbContent content : contentList) {
            AdNode node = new AdNode();
            node.setHeight(240);
            node.setWidth(800);
            node.setSrc(content.getPic());

            node.setHeightB(240);
            node.setWidth(700);
            node.setSrcB(content.getPic2());

            node.setHref(content.getUrl());
            node.setAlt(content.getSubTitle());

            // 放到nodeList集合中
            nodeList.add(node);
        }
        // 将List<adNode>转换成json
        String nodeJson = JsonUtils.objectToJson(nodeList);
        return nodeJson;
    }
}
