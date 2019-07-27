package com.osyunge.portal.service;

import com.osyunge.dataobject.SearchResult;

public interface SearchService {
    SearchResult search(String queryString, Integer page);
}
