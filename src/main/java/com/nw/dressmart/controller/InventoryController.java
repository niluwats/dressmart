package com.nw.dressmart.controller;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;
import com.nw.dressmart.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/stock")
    public ResponseEntity<InventoryDto> createStock(
            @RequestBody @Valid InventoryRequestDto inventoryRequest){
        return new ResponseEntity<InventoryDto>(inventoryService.addNewStock(inventoryRequest), HttpStatus.CREATED);
    }

    @GetMapping("/stock")
    public ResponseEntity<List<InventoryDto>>getStocks(){
        return ResponseEntity.ok(inventoryService.getStocks());
    }

    @GetMapping("/stock/{stockId}")
    public ResponseEntity<InventoryDto>getStock(@PathVariable("stockId")Long id){
        return ResponseEntity.ok(inventoryService.getStock(id));
    }

    @GetMapping("{itemId}")
    public ResponseEntity<List<InventoryDto>>getItemStocks(@PathVariable("itemId")Long id){
        return ResponseEntity.ok(inventoryService.getItemStocks(id));
    }

    @PutMapping("/stock/{stockId}")
    public ResponseEntity<InventoryDto> updateStock(
            @PathVariable("stockId") Long id,
            @RequestBody @Valid InventoryRequestDto inventoryRequest){
        return new ResponseEntity<InventoryDto>(inventoryService.updateInventory(id,inventoryRequest),HttpStatus.OK);
    }

    @DeleteMapping("/stock/{stockId}")
    public String deleteStock(@PathVariable("stockId") Long id){
        return inventoryService.deleteStock(id);
    }
}
