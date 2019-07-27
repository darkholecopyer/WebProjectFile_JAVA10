package com.osyunge.service.impl;

import com.osyunge.dataobject.EasyUITreeNode;
import com.osyunge.dataobject.FCResult;
import com.osyunge.mapper.TbContentCategoryMapper;
import com.osyunge.pojo.TbContentCategory;
import com.osyunge.pojo.TbContentCategoryExample;
import com.osyunge.service.ItemContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ItemContentCategoryServiceImpl implements ItemContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
        for (TbContentCategory contentCategory : contentCategoryList){
            EasyUITreeNode treeNode = new EasyUITreeNode();

            treeNode.setId(contentCategory.getId());
            treeNode.setParentId(contentCategory.getParentId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getIsParent()?"closed":"open");
            easyUITreeNodeList.add(treeNode);
        }
        return easyUITreeNodeList;
    }

    @Override
    public FCResult insertCategory(Long parentId, String name) {
        //创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //1正常2删除
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        //排列序号，表示通级类目的展现次序，如数值相等，则按名称次序排列
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入数据,这个方法需要对他改变
        contentCategoryMapper.insert(contentCategory);
        //取返回的主键
        Long id = contentCategory.getId();
        //判断父节点的isParent属性
        //查询父节点，如果有父节点，直接返回结果，如果没有，就将当前节点设置为父节点，并更新
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()){
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        return FCResult.ok(id);
    }

    @Override
    public FCResult updateCategory(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        System.err.println("id="+id+",name="+name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return FCResult.ok(id);
    }

    @Override
    public FCResult deleteCategory(Long parentId, Long id) {
        deleteContentCategory(parentId,id);
        return FCResult.ok(id);
    }
    public void deleteContentCategory(Long parentId, Long id){
        //判断是否是父节点，并删除
        TbContentCategory contentCategory1 = contentCategoryMapper.selectByPrimaryKey(id);
        if (contentCategory1.getIsParent()==true){
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory contentCategory : contentCategoryList){
                deleteContentCategory(contentCategory.getParentId(),contentCategory.getId());
            }
            changeIsParent(parentId);
            contentCategoryMapper.deleteByPrimaryKey(id);
            changeIsParent(parentId);
        }else {
            contentCategoryMapper.deleteByPrimaryKey(id);
            changeIsParent(parentId);
        }
    }
    public void changeIsParent(long parentId){
        //查询父节点是否还有子节点，如果有则不变，没有，就改变isParent属性为false
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
        if (contentCategoryList==null||contentCategoryList.size()==0){
            TbContentCategory contentCategory = new TbContentCategory();
            contentCategory.setId(parentId);
            contentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        }
    }

}
