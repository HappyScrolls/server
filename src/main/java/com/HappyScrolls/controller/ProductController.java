package com.HappyScrolls.controller;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "제품 생성")
    @PostMapping("")
    public ResponseEntity createProduct(@RequestBody ProductDTO.Request request) {

        ProductDTO.Response response = productService.productCreate(request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "제품 id로 단건 조회")
    @GetMapping("")
    public ResponseEntity retrieveProduct(@RequestParam Long id) {
        ProductDTO.Response response = productService.productRetrieve(id);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "모든 제품 조회")
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO.Response>> retrieveAllProduct() {
        List<ProductDTO.Response> response = productService.productAllRetrieve();
        return new ResponseEntity<List<ProductDTO.Response>>(response, HttpStatus.ACCEPTED);
    }
}
