package com.example.demo.catalog.utils;

import com.example.demo.catalog.model.Product;
import com.example.demo.catalog.model.ProductDto;

public class ProductConverter {
    private ProductConverter() {}

    public static ProductDto convert(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSalePrice(product.getSalePrice());
        dto.setCategory(product.getCategory());
        dto.setCategoryTree(product.getCategoryTree());
        dto.setAvgRating(product.getAvgRating());
        dto.setProductUrl(product.getProductUrl());
        dto.setProductImgUrl(product.getProductImgUrl());
        dto.setBrand(product.getBrand());
        dto.setTotalReviews(product.getTotalReviews());
        return dto;
    }
}
