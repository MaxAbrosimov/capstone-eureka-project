package com.example.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

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
