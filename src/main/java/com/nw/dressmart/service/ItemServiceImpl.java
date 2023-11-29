package com.nw.dressmart.service;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequest;
import com.nw.dressmart.entity.Category;
import com.nw.dressmart.entity.Item;
import com.nw.dressmart.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements  ItemService{
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Override
    public ItemDto createItem(ItemRequest itemRequest) {
        Optional<Item> optionalItem=itemRepository.findByName(itemRequest.getName());
        if(optionalItem.isPresent()){
            throw new IllegalStateException("item name "+itemRequest.getName()+" already exists");
        }

        Item item=modelMapper.map(itemRequest,Item.class);
        itemRepository.save(item);
        return modelMapper.map(item,ItemDto.class);
    }

    @Override
    public List<ItemDto> viewItems() {
        return null;
    }

    @Override
    public ItemDto viewItem(Long id) {
        return null;
    }

    @Override
    public String updateItem(Long id, ItemRequest itemRequest) {
        return null;
    }

    @Override
    public String deleteItem(Long id) {
        return null;
    }

    private ItemDto convertToDto(Item item){
        return modelMapper.map(item,ItemDto.class);
    }
}
