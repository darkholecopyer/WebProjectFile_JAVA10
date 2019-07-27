package com.osyunge.service;

import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.dataobject.FCResult;

public interface ItemParamService {
    EasyUIDataGridResult selectItemParamList(int page,int row);

    FCResult queryItemCatId(long itemcatid);

    FCResult insertItemParam(Long cid,String paramData);

    int deleteItemParamById(long id);
}
