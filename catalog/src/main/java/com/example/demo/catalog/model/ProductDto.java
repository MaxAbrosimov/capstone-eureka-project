package com.example.demo.catalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ProductDto {

    private String id;

    private String sku;

    private String name;

    private String description;

    private String price;

    private String salePrice;

    private String category;

    private String categoryTree;

    private String avgRating;

    private String productUrl;

    private String productImgUrl;

    private String brand;

    private String totalReviews;
}
