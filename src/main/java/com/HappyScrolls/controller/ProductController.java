package com.HappyScrolls.controller;

import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "제품 생성")
    @PostMapping("")
    public ResponseEntity<Long> createProduct(@RequestBody ProductDTO.Request request) {
        Long response = productService.productCreate(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "제품 id로 단건 조회")
    @GetMapping("")
    public ResponseEntity<ProductDTO.Response> retrieveProduct(@RequestParam Long id) {
        return ResponseEntity.ok(productService.productRetrieve(id));
    }

    @ApiOperation(value = "모든 제품 조회")
    @GetMapping("/all")
    public ResponseEntity<List<Long>> retrieveAllProduct() {
        return ResponseEntity.ok(productService.productAllRetrieve());
    }



}
