package com.nw.dressmart.controller;

import com.nw.dressmart.dto.ProductDto;
import com.nw.dressmart.dto.ProductRequestDto;
import com.nw.dressmart.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductRequestDto request){
        return new ResponseEntity<ProductDto>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> viewProducts(){
        return  ResponseEntity.ok(productService.viewProducts());
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> viewProduct(@PathVariable("productId") Long id){
        return ResponseEntity.ok(productService.viewProduct(id));
    }

    @PutMapping("{productId}")
    public String updateProduct(@PathVariable("productId") Long id,
                                 @RequestBody @Valid ProductRequestDto productRequest){
        return productService.updateProduct(id,productRequest);
    }

    @DeleteMapping("{productId}")
    public String deleteProduct(@PathVariable("productId") Long id){
        return productService.deleteProduct(id);
    }


}
