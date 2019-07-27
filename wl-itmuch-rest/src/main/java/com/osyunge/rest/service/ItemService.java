package com.osyunge.rest.service;

import com.osyunge.dataobject.FCResult;

public interface ItemService {
    FCResult getItemBaseInfo(long itemId);
    FCResult getItemDesc(long itemId);
    FCResult getItemParam(long itemId);
}
