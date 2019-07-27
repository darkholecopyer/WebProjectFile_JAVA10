package com.osyunge.search.service.impl;

import com.osyunge.dataobject.FCResult;
import com.osyunge.dataobject.Item;
import com.osyunge.search.mapper.ItemMapper;
import com.osyunge.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public FCResult getItemList() {
        List<com.osyunge.dataobject.Item> itemList = itemMapper.getItemList();
        try {
            for (Item item: itemList
            ) {
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSell_point());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategory_name());
                document.setField("item_desc", item.getItem_des());
                //写入索引库
                solrServer.add(document);
            }
            solrServer.commit();
        }catch (Exception e){
            e.printStackTrace();
            FCResult.build(500,"查询异常");
        }
        return FCResult.ok();
    }
}
