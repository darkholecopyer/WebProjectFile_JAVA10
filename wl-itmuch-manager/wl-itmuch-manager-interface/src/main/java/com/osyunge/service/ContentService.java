package com.osyunge.service;

import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.pojo.TbContent;

public interface ContentService {
    EasyUIDataGridResult queryList(long categoryId, int page, int rows);

    void saveContent(TbContent content);

    void deleteContent(long id);

    void editContent(TbContent content);
}
