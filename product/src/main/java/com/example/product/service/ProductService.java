package com.example.product.service;


import com.example.product.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> getProductsByIds(Set<String> ids);

    List<Product> getProductsBySku(String sku);

}
