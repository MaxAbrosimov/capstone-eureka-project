package com.example.product.service;

import com.example.product.client.InventoryClient;
import com.example.product.client.CatalogClient;
import com.example.product.model.ApplicationException;
import com.example.product.model.Product;
import com.netflix.client.ClientException;
import feign.FeignException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private CatalogClient catalogClient;
    private InventoryClient inventoryClient;

    @Value("${catalog.application.name}")
    private String catalogApplicationName;

    @Value("${inventory.application.name}")
    private String inventoryApplicationName;

    @Autowired
    public ProductServiceImpl(CatalogClient catalogClient, InventoryClient inventoryClient) {
        this.catalogClient = catalogClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public List<Product> getProductsByIds(Set<String> ids) {
        List<Product> products = fetchProducts(ids);
        Map<String, Boolean> availabilities = getAvailabilities(ids);

        return getFilteredProducts(products, availabilities);
    }

    @Override
    public List<Product> getProductsBySku(String sku) {
        List<Product> products = catalogClient.getProductsBySku(sku);
        Set<String> productIds = products.stream().map(Product::getId).collect(Collectors.toSet());

        Map<String, Boolean> availabilities = getAvailabilities(productIds);

        return getFilteredProducts(products, availabilities);
    }

    @SneakyThrows
    private List<Product> fetchProducts(Set<String> ids) {
        try {
            return catalogClient.getProductsByIds(ids);
        } catch (Exception e) {
            log.error("{} service throws exception", catalogApplicationName, e);
            if (e instanceof FeignException || e.getCause() instanceof ClientException) {
                throw new ApplicationException(String.format("Service %s throws exception. %s", catalogApplicationName, e) ,e);
            }
            throw e;
        }
    }

    @SneakyThrows
    private Map<String, Boolean> getAvailabilities(Set<String> productIds) {
        try {
            return inventoryClient.getAvailabilities(productIds);
        } catch (Exception e) {
            log.error("{} service throws exception", inventoryApplicationName, e);
            if (e instanceof FeignException || e.getCause() instanceof ClientException) {
                throw new ApplicationException(String.format("Service %s throws exception. %s", inventoryApplicationName, e) ,e);
            }
            throw e;
        }
    }

    private List<Product> getFilteredProducts(List<Product> products, Map<String, Boolean> availabilities) {
        return products.stream().filter(product -> availabilities.get(product.getId())).collect(Collectors.toList());
    }
}
