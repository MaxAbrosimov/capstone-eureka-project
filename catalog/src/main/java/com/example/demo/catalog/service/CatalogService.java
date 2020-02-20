package com.example.demo.catalog.service;

import com.example.demo.catalog.model.ProductDto;

import java.util.List;
import java.util.Set;

public interface CatalogService {

    List<ProductDto> getProductsByIds(Set<String> ids);

    List<ProductDto> getProductsBySku(String sku);
}
