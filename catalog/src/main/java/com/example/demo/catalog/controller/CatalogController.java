package com.example.demo.catalog.controller;

import com.example.demo.catalog.model.ProductDto;
import com.example.demo.catalog.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private static Logger log = LoggerFactory.getLogger(CatalogController.class);

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(value = "/sku/{sku}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<ProductDto> getProductsBySku(@PathVariable(value = "sku") String sku) {
        log.info("Loading products by sku {}", sku);
        List<ProductDto> products = catalogService.getProductsBySku(sku);
        log.info("Product loaded by sku: {}", products);
        return products;
    }

    @GetMapping(value = "/{ids}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<ProductDto> getProductsByIds(@PathVariable(value = "ids") Set<String> ids) {
        log.info("Loading products by ids {}", ids);
        List<ProductDto> products = catalogService.getProductsByIds(ids);
        log.info("Loaded products by ids {}", products);
        return products;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<ProductDto> getAll() {
        log.info("Loading all products");
        List<ProductDto> products = catalogService.getProductsByIds(null);
        log.info("Loaded all products: {}", products);
        return products;
    }

}
