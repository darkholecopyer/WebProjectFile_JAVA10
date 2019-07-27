package com.osyunge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.dataobject.FCResult;
import com.osyunge.mapper.TbItemParamMapper;
import com.osyunge.pojo.TbItemParam;
import com.osyunge.pojo.TbItemParamExample;
import com.osyunge.pojo.TbItemParamPlus;
import com.osyunge.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    @Override
    public EasyUIDataGridResult selectItemParamList(int page, int row) {
        PageHelper.startPage(page,row);
        List<TbItemParamPlus> list = tbItemParamMapper.selectItemParam();
        PageInfo<TbItemParamPlus> info = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(info.getTotal());
        return result;
    }

    @Override
    public FCResult queryItemCatId(long itemcatid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemcatid);

        //执行查询
        List<TbItemParam> list = tbItemParamMapper .selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list!=null&&list.size()>0){
            TbItemParam itemParam = list.get(0);
            return FCResult.ok(itemParam);
        }
        return FCResult.ok();
    }

    @Override
    public FCResult insertItemParam(Long cid,String paramData) {
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());

        tbItemParamMapper.insert(itemParam);
        return FCResult.ok();
    }

    @Override
    public int deleteItemParamById(long id) {
        return tbItemParamMapper.deleteByPrimaryKey(id);
    }
}
