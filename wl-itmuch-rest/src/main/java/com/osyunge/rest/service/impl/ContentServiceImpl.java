package com.osyunge.rest.service.impl;

import com.osyunge.mapper.TbContentMapper;
import com.osyunge.pojo.TbContent;
import com.osyunge.pojo.TbContentExample;
import com.osyunge.rest.dao.JedisClient;
import com.osyunge.rest.service.ContentService;
import com.osyunge.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public List<TbContent> getContentList(Long contentCid) {
        //从缓存中取内容
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if (!StringUtils.isBlank(result)) {
                //把字符串转换成list
                List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);

        //向缓存中添加内容
        try {
            //把list转换成字符串
            String cacheString = JsonUtils.objectToJson(list);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid + "", cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
