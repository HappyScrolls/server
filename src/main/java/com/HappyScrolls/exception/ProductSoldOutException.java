package com.HappyScrolls.exception;


public class ProductSoldOutException extends RuntimeException {
    public ProductSoldOutException(String message) {
        super(message);
    }
}