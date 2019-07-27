package com.osyunge.portal.service;

import com.osyunge.dataobject.FCResult;
import com.osyunge.pojo.TbItem;

public interface ItemService {
    TbItem getItemById(Long itemId);

    String getItemDescById(Long itemId);

    //根据id查询规格参数
    String getItemParam(Long itemId);
}
