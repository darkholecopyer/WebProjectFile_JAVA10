package com.osyunge.service.impl;

import com.osyunge.mapper.TbItemDescMapper;
import com.osyunge.pojo.TbItemDesc;
import com.osyunge.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public int addItemDesc(String desc, long itemId) {
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);

        return tbItemDescMapper.insertSelective(tbItemDesc);
    }

    @Override
    public TbItemDesc getItemDescById(long descId) {
        return tbItemDescMapper.selectByPrimaryKey(descId);
    }

    @Override
    public int updateTbDesc(Long id, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        return tbItemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }
}
