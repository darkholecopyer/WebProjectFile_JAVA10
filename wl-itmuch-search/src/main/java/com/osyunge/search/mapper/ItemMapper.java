package com.osyunge.search.mapper;

import com.osyunge.dataobject.Item;

import java.util.List;

public interface ItemMapper {
    List<Item> getItemList();

    Item selectItem(Long itemId);

}
