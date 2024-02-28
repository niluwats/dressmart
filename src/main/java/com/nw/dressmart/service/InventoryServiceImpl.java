package com.nw.dressmart.service;

import com.nw.dressmart.dto.InventoryDto;
import com.nw.dressmart.dto.InventoryRequestDto;
import com.nw.dressmart.entity.Inventory;
import com.nw.dressmart.entity.Product;
import com.nw.dressmart.mappers.CustomInventoryMapper;
import com.nw.dressmart.repository.InventoryRepository;
import com.nw.dressmart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomInventoryMapper inventoryMapper;

    @Override
    public InventoryDto addNewStock(InventoryRequestDto inventoryRequest) {
        Product product=productRepository.findById(inventoryRequest.getProductId()).orElseThrow(()->
                new IllegalStateException("product id "+inventoryRequest.getProductId()+" not found"));

        Inventory inventoryProduct=inventoryMapper.inventoryDtoToInventoryProduct(inventoryRequest);
        inventoryProduct.setProduct(product);

        inventoryRepository.save(inventoryProduct);
        return inventoryMapper.inventoryProductToInventoryDto(inventoryProduct);
    }

    @Override
    public InventoryDto getStock(Long id) {
        Inventory inventoryProduct=inventoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("inventory id "+id+" not found"));

        return inventoryMapper.inventoryProductToInventoryDto(inventoryProduct);
    }

    @Override
    public List<InventoryDto> getStocks() {
        List<Inventory> inventoryProducts=inventoryRepository.findAll();
        return inventoryProducts.stream().map(inventoryMapper::inventoryProductToInventoryDto).collect(Collectors.toList());
    }

    @Override
    public List<InventoryDto> getProductStocks(Long productId) {
        List<Inventory> inventoryProducts=inventoryRepository.findAllByProduct_Id(productId);
        return inventoryProducts.stream().map(inventoryMapper::inventoryProductToInventoryDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public InventoryDto updateInventory(Long id, InventoryRequestDto inventoryRequest) {
        Long productId=inventoryRequest.getProductId();

        Inventory inventoryProduct=inventoryRepository.findById(id).
                orElseThrow(()->new IllegalStateException("stock id "+id+" not found"));

        inventoryProduct.setQuantity(inventoryRequest.getQuantity());

        if(!Objects.equals(productId,inventoryProduct.getProduct().getId())){
            Product product=productRepository.findById(productId).orElseThrow(()->
                    new IllegalStateException("product id "+productId+" not found"));

            inventoryProduct.setProduct(product);
        }

        return inventoryMapper.inventoryProductToInventoryDto(inventoryProduct);
    }

    @Override
    public String deleteStock(Long id) {
        inventoryRepository.findById(id).
                orElseThrow(()->new IllegalStateException("stock id "+id+" not found"));

        System.out.println("stock id "+id);
        inventoryRepository.deleteById(id);
        return "deleted";
    }
}
