package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequestDto;
import com.nw.dressmart.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

public interface ItemMapper {
    ItemDto itemToItemDto(Item item);

    Item itemDtoToItem(ItemRequestDto itemRequestDto);
}
