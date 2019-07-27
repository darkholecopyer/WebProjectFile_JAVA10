package com.osyunge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.mapper.TbItemMapper;
import com.osyunge.pojo.TbItem;
import com.osyunge.pojo.TbItemExample;
import com.osyunge.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public TbItem selectItemInfoById(long itemid) {
        TbItemExample itemExample = new TbItemExample();
        TbItemExample.Criteria criteria = itemExample.createCriteria();
        //设置条件
        criteria.andIdEqualTo(itemid);
        //执行查询
        List<TbItem> tbItems = itemMapper.selectByExample(itemExample);
        TbItem item = null;
        if (tbItems.size()>0){
            item = tbItems.get(0);
        }
        return item;
    }
    //第一个视频24分27秒

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public int addItem(TbItem tbItem) {
        return itemMapper.insertSelectiveReturnId(tbItem);
    }

    @Override
    public int instockItemById(long l) {
        TbItem tbItem = new TbItem();
        tbItem.setId(l);
        tbItem.setStatus(Byte.parseByte("2"));
        return itemMapper.updateByPrimaryKeySelective(tbItem);
    }

    @Override
    public int reshelfItemById(long l) {
        TbItem tbItem = new TbItem();
        tbItem.setId(l);
        tbItem.setStatus(Byte.parseByte("1"));
        return itemMapper.updateByPrimaryKeySelective(tbItem);
    }

    @Override
    public int deleteItemById(long l) {
        return itemMapper.deleteByPrimaryKey(l);
    }

    @Override
    public int updateTbItem(TbItem tbItem) {
        return itemMapper.updateByPrimaryKeySelective(tbItem);
    }


}
