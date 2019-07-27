package com.osyunge.service;

import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.pojo.TbItem;

public interface ItemService {
    //根据商品id查询商品信息
    TbItem selectItemInfoById(long itemid);
    EasyUIDataGridResult getItemList(int page, int rows);

    int addItem(TbItem tbItem);

    int instockItemById(long l);

    int reshelfItemById(long l);

    int deleteItemById(long l);

    int updateTbItem(TbItem tbItem);
}
