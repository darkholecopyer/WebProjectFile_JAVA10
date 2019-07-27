package com.osyunge.search.dao;

import com.osyunge.dataobject.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchDao {
    SearchResult search(SolrQuery query) throws SolrServerException;
}
