package com.example.demo.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;


@Data
@JsonPropertyOrder({ "uniq_id", "sku", "name_title","description","list_price","sale_price","category","category_tree","average_product_rating","product_url","product_image_urls","brand","total_number_reviews" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @JsonProperty("uniq_id")
    private String id;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("name_title")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("list_price")
    private String price;

    @JsonProperty("sale_price")
    private String salePrice;

    @JsonProperty("category")
    private String category;

    @JsonProperty("category_tree")
    private String categoryTree;

    @JsonProperty("average_product_rating")
    private String avgRating;

    @JsonProperty("product_url")
    private String productUrl;

    @JsonProperty("product_image_urls")
    private String productImgUrl;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("total_number_reviews")
    private String totalReviews;

}
