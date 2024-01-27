package com.nw.dressmart.service;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequestDto;

import java.util.List;

public interface ItemService{
    ItemDto createItem(ItemRequestDto itemRequest);

    List<ItemDto> viewItems();

    ItemDto viewItem(Long id);

    String updateItem(Long id, ItemRequestDto itemRequest);

    String deleteItem(Long id);
}
