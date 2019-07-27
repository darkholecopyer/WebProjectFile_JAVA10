package com.osyunge.search.service;

import com.osyunge.dataobject.FCResult;
import com.osyunge.dataobject.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchService {
    SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException;

    FCResult updateItemById(Long itemId);

}
