package com.osyunge.service;

import com.osyunge.dataobject.EasyUITreeNode;
import com.osyunge.dataobject.FCResult;

import java.util.List;

public interface ItemContentCategoryService {
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    FCResult insertCategory(Long parentId, String name);

    FCResult updateCategory(Long parentId, String name);

    FCResult deleteCategory(Long parentId, Long id);
}
