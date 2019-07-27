package com.osyunge.rest.service;

import com.osyunge.pojo.TbContent;

import java.util.List;

public interface ContentService {
    List<TbContent> getContentList(Long cid);
}
