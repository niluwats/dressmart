package com.nw.dressmart.service;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequest;

import java.util.List;

public interface ItemService{
    ItemDto createItem(ItemRequest itemRequest);

    List<ItemDto> viewItems();

    ItemDto viewItem(Long id);

    String updateItem(Long id,ItemRequest itemRequest);

    String deleteItem(Long id);
}
