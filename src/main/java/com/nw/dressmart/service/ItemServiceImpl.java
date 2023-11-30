package com.nw.dressmart.service;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequest;
import com.nw.dressmart.entity.Category;
import com.nw.dressmart.entity.Item;
import com.nw.dressmart.repository.CategoryRepository;
import com.nw.dressmart.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements  ItemService{
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ItemDto createItem(ItemRequest itemRequest) {
        Optional<Item> optionalItem=itemRepository.findByName(itemRequest.getName());
        if (optionalItem.isPresent()) {
            throw new IllegalStateException("item name "+itemRequest.getName()+" already exists");
        }

        Category category=categoryRepository.findById(itemRequest.getCategoryId()).orElseThrow(()->
                new IllegalStateException("category with ID "+itemRequest.getCategoryId()+" doesn't exists"));

        Item item=convertToEntity(itemRequest,category);

        itemRepository.save(item);

        return convertToDto(item);
    }

    @Override
    public List<ItemDto> viewItems() {
        List<Item> items=itemRepository.findAll();
        return  items.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto viewItem(Long id) {
       Item item= itemRepository.findById(id).orElseThrow(()->
                new IllegalStateException("item with ID "+id+" doesn't exists"));

        return convertToDto(item);
    }

    @Transactional
    @Override
    public String updateItem(Long id, ItemRequest itemRequest) {
        Item item=itemRepository.findById(id).orElseThrow(()->
                new IllegalStateException("item with ID "+id+" doesn't exists"));

        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());

        if(!Objects.equals(itemRequest.getCategoryId(),item.getCategory().getId())){
            Category category=categoryRepository.findById(itemRequest.getCategoryId()).orElseThrow(()->
                    new IllegalStateException("category with ID "+itemRequest.getCategoryId()+" doesn't exists"));
            item.setCategory(category);
        }

        return "updated";
    }

    @Override
    public String deleteItem(Long id) {
        itemRepository.findById(id).orElseThrow(()->
                new IllegalStateException("item with ID "+id+" doesn't exists"));

        itemRepository.deleteById(id);
        return "deleted";
    }

    private Item convertToEntity(ItemRequest itemRequest,Category category){
        Item item=new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());
        item.setCategory(category);
        item.setCreatedOn(LocalDateTime.now());
        item.setStatus(true);

        return item;
    }

    private ItemDto convertToDto(Item item){
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
}
