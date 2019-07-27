package com.osyunge.search.service.impl;

import com.osyunge.dataobject.FCResult;
import com.osyunge.dataobject.Item;
import com.osyunge.dataobject.SearchResult;
import com.osyunge.search.dao.SearchDao;
import com.osyunge.search.mapper.ItemMapper;
import com.osyunge.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 写入索引库
     * @param queryString
     * @param page
     * @param rows
     * @return
     * @throws SolrServerException
     */
    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException {
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(queryString);
        //设置分页
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_keywords");
        //设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算查询结果总页数
        long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows > 0){
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(page);
        return searchResult;
    }

    /**
     * 更新索引库
     * @param itemId
     * @return
     */
    @Override
    public FCResult updateItemById(Long itemId) {
        Item item = itemMapper.selectItem(itemId);
        SolrInputDocument document = new SolrInputDocument();
        try {
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            document.addField("item_desc", item.getItem_des());
            solrServer.add(document);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FCResult.ok();
    }

}
