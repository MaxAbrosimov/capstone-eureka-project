package com.example.demo.catalog.service;

import com.example.demo.catalog.dao.CatalogLoader;
import com.example.demo.catalog.model.ProductDto;
import com.example.demo.catalog.utils.ProductConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private static Logger log = LoggerFactory.getLogger(CatalogServiceImpl.class);

    private List<ProductDto> loadedProducts;

    private final CatalogLoader catalogLoader;

    @Autowired
    public CatalogServiceImpl(CatalogLoader catalogLoader) {
        this.catalogLoader = catalogLoader;
    }

    @Override
    public List<ProductDto> getProductsByIds(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.info("Return all products");
            return loadedProducts;
        }
        return loadedProducts.stream().filter(product -> ids.contains(product.getId())).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsBySku(String sku) {
        return loadedProducts.stream().filter(product -> sku.equals(product.getSku())).collect(Collectors.toList());
    }

    @PostConstruct
    void loadAllProducts() throws IOException {
        log.info("Loading products from csv");
        loadedProducts = catalogLoader.getProducts().stream().map(ProductConverter::convert).collect(Collectors.toList());
        log.info("Loaded products from csv: {}", loadedProducts);
    }
}
