package com.nw.dressmart.controller;

import com.nw.dressmart.dto.ItemDto;
import com.nw.dressmart.dto.ItemRequest;
import com.nw.dressmart.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody @Valid ItemRequest request){
        return new ResponseEntity<ItemDto>(itemService.createItem(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> viewItems(){
        return  ResponseEntity.ok(itemService.viewItems());
    }

    @GetMapping("{itemId}")
    public ResponseEntity<ItemDto> viewItem(@PathVariable("itemId") Long id){
        return ResponseEntity.ok(itemService.viewItem(id));
    }

    @PutMapping("{itemId}")
    public String updateItem(@PathVariable("itemId") Long id,
                                 @RequestBody @Valid ItemRequest itemRequest){
        return itemService.updateItem(id,itemRequest);
    }

    @DeleteMapping("{itemId}")
    public String deleteItem(@PathVariable("itemId") Long id){
        return itemService.deleteItem(id);
    }


}