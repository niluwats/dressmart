package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequestDto;
import com.nw.dressmart.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class CustomItemMapper implements ItemMapper{
    @Override
    public ItemDto itemToItemDto(Item item) {
        ItemDto itemDto=new ItemDto();
       itemDto.setId(item.getId());
       itemDto.setName(item.getName());
       itemDto.setDescription(item.getDescription());
       itemDto.setPrice(item.getPrice());
       itemDto.setCategoryName(item.getCategory().getName());
       itemDto.setStatus(item.getStatus());
       itemDto.setCreatedOn(item.getCreatedOn());
       return  itemDto;
    }

    @Override
    public Item itemDtoToItem(ItemRequestDto itemRequestDto) {
        Item item=new Item();
        item.setName(itemRequestDto.getName());
        item.setDescription(itemRequestDto.getDescription());
        item.setPrice(itemRequestDto.getPrice());
        item.setCreatedOn(LocalDateTime.now());
        item.setStatus(true);
        return item;
    }
}
