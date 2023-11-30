package com.nw.dressmart.controller;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequest;
import com.nw.dressmart.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> createStock(
            @RequestBody @Valid InventoryRequest inventoryRequest){
        return new ResponseEntity<InventoryDto>(inventoryService.addNewStock(inventoryRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>>getStocks(){
        return ResponseEntity.ok(inventoryService.getStocks());
    }

    @GetMapping("{stockId}")
    public ResponseEntity<InventoryDto>getStock(@PathVariable("stockId")Long id){
        return ResponseEntity.ok(inventoryService.getStock(id));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<InventoryDto>>getItemStocks(@PathVariable("itemId")Long id){
        return ResponseEntity.ok(inventoryService.getItemStocks(id));
    }

    @PutMapping("{stockId}")
    public ResponseEntity<InventoryDto> updateStock(
            @PathVariable("stockId") Long id,
            @RequestBody @Valid InventoryRequest inventoryRequest){
        return new ResponseEntity<InventoryDto>(inventoryService.updateInventory(id,inventoryRequest),HttpStatus.OK);
    }

    @DeleteMapping("{stockId}")
    public String deleteStock(@PathVariable("stockId") Long id){
        return inventoryService.deleteStock(id);
    }
}
