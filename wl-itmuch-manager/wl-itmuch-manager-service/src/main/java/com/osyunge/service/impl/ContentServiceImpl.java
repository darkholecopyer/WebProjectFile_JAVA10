package com.osyunge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.mapper.TbContentMapper;
import com.osyunge.pojo.TbContent;
import com.osyunge.pojo.TbContentExample;
import com.osyunge.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public EasyUIDataGridResult queryList(long categoryId, int page, int rows) {
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public void saveContent(TbContent content) {
        contentMapper.insertSelective(content);
    }

    @Override
    public void deleteContent(long id) {
        contentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void editContent(TbContent content) {
        contentMapper.updateByPrimaryKeyWithBLOBs(content);
    }
}
