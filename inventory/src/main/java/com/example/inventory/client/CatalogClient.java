package com.example.inventory.client;

import com.example.inventory.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "${catalog.application.name}")
public interface CatalogClient {

    @GetMapping(value = "catalog/all", produces = APPLICATION_JSON_VALUE)
    List<Product> getAll();
}
