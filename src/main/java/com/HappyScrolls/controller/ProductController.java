package com.HappyScrolls.controller;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("")
    public ResponseEntity createProduct(@RequestBody ProductDTO.Request request) {

        ProductDTO.Response response = productService.productCreate(request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity retrieveProduct(@RequestParam Long id) {
        ProductDTO.Response response = productService.productRetrieve(id);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }
}
