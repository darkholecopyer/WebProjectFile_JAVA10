package com.osyunge.rest.service.impl;

import com.osyunge.dataobject.FCResult;
import com.osyunge.mapper.TbItemDescMapper;
import com.osyunge.mapper.TbItemMapper;
import com.osyunge.mapper.TbItemParamItemMapper;
import com.osyunge.pojo.TbItem;
import com.osyunge.pojo.TbItemDesc;
import com.osyunge.pojo.TbItemParamItem;
import com.osyunge.pojo.TbItemParamItemExample;
import com.osyunge.rest.dao.JedisClient;
import com.osyunge.rest.service.ItemService;
import com.osyunge.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public FCResult getItemBaseInfo(long itemId) {
        try {
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
            //判断是否有值
            if (!StringUtils.isBlank(json)){
                //把json转换成json对象
                TbItem item = JsonUtils.jsonToPojo(json,TbItem.class);
                return FCResult.ok(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据商品id查询商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //使用FCResult包装一下
        try {
            //把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base",JsonUtils.objectToJson(item));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base",REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return FCResult.ok(item);
    }

    @Override
    public FCResult getItemDesc(long itemId) {
        //添加缓存
        try {
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
            //判断是否有值
            if (!StringUtils.isBlank(json)){
                //把json转换为java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json,TbItemDesc.class);
                return FCResult.ok(itemDesc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //创建查询条件
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try{
            //把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc",JsonUtils.objectToJson(itemDesc));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc",REDIS_ITEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return FCResult.ok(itemDesc);
    }

    @Override
    public FCResult getItemParam(long itemId) {
        //添加缓存
        try {
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":param");
            //判断是否有值
            if (!StringUtils.isBlank(json)){
                //把json转换成java对象
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json,TbItemParamItem.class);
                return FCResult.ok(paramItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据商品id查询规格参数
        //设置查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size()>0){
            TbItemParamItem paramItem = list.get(0);
            try{
                //把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param",JsonUtils.objectToJson(paramItem));
                //设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param",REDIS_ITEM_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return FCResult.ok(paramItem);
        }
        return FCResult.build(400,"无此商品规格");
    }
}
