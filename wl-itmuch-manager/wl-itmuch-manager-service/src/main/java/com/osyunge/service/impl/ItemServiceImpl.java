package com.osyunge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.dataobject.FCResult;
import com.osyunge.mapper.TbItemMapper;
import com.osyunge.mapper.TbItemParamItemMapper;
import com.osyunge.pojo.TbItem;
import com.osyunge.pojo.TbItemExample;
import com.osyunge.pojo.TbItemParamItem;
import com.osyunge.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
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

    //添加规格参数
    @Override
    public FCResult insertItemParamItem(Long itemId,String itemParam){
        //创建一个pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向表中插入数据
        itemParamItemMapper.insert(itemParamItem);
        return FCResult.ok();
    }
}
