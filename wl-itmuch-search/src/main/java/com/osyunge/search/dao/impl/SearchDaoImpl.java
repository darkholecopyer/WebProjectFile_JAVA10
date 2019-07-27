package com.osyunge.search.dao.impl;

import com.osyunge.dataobject.Item;
import com.osyunge.dataobject.SearchResult;
import com.osyunge.search.dao.SearchDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult search(SolrQuery query) throws SolrServerException {
        //返回值对象
        SearchResult result = new SearchResult();
        //根据查询条件查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取查询结果
        //取查询结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());
        //商品列表
        List<Item> itemList = new ArrayList<>();
        //取高亮显示
        Map<String,Map<String,List<String>>> highlighting = queryResponse.getHighlighting();
        //取商品列表
        for (SolrDocument solrDocument: solrDocumentList) {
            //创建一商品对象
            Item item = new Item();
            item.setId((String)solrDocument.get("id"));
            //取高亮显示的结果
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size()>0){//如果有设置高亮就直接取
                title = list.get(0);
            }else {//如果没有就直接显示标题
                title = (String)solrDocument.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String)solrDocument.get("item_image"));
            item.setPrice((long)solrDocument.get("item_price"));
            item.setSell_point((String)solrDocument.get("item_sell_point"));
            item.setCategory_name((String)solrDocument.get("item_category_name"));
            //添加的商品列表
            itemList.add(item);
        }
        result.setItemList(itemList);
        return result;
    }
}
