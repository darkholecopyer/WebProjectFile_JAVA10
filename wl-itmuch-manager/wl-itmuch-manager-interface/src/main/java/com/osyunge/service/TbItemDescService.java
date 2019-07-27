package com.osyunge.service;

import com.osyunge.pojo.TbItemDesc;

public interface TbItemDescService {
    int addItemDesc(String desc, long itemId);

    TbItemDesc getItemDescById(long descId);

    int updateTbDesc(Long id, String desc);
}
