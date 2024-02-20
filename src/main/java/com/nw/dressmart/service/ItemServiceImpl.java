package com.nw.dressmart.service;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequestDto;
import com.nw.dressmart.entity.Category;
import com.nw.dressmart.entity.Item;
import com.nw.dressmart.mappers.CustomItemMapper;
import com.nw.dressmart.mappers.ItemMapper;
import com.nw.dressmart.repository.CategoryRepository;
import com.nw.dressmart.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements  ItemService{
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomItemMapper itemMapper;

    @Override
    public ItemDto createItem(ItemRequestDto itemRequest) {
        Optional<Item> optionalItem=itemRepository.findByName(itemRequest.getName());
        if (optionalItem.isPresent()) {
            throw new IllegalStateException("item name "+itemRequest.getName()+" already exists");
        }

        Category category=categoryRepository.findById(itemRequest.getCategoryId()).orElseThrow(()->
                new IllegalStateException("category with ID "+itemRequest.getCategoryId()+" doesn't exists"));

        Item item=itemMapper.itemDtoToItem(itemRequest);
        item.setCategory(category);

        System.out.println("status of item ********"+item.getStatus());
        itemRepository.save(item);

        return itemMapper.itemToItemDto(item);
    }

    @Override
    public List<ItemDto> viewItems() {
        List<Item> items=itemRepository.findAll();
        return  items.stream().map(itemMapper::itemToItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto viewItem(Long id) {
       Item item= itemRepository.findById(id).orElseThrow(()->
                new IllegalStateException("item with ID "+id+" doesn't exists"));

        return itemMapper.itemToItemDto(item);
    }

    @Transactional
    @Override
    public String updateItem(Long id, ItemRequestDto itemRequest) {
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
}
