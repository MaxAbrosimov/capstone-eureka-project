package com.example.product.client;

import com.example.product.model.Product;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.ServiceUnavailableException;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "${catalog.application.name}")
@RibbonClient(name = "${catalog.application.name}")
public interface CatalogClient {

    @GetMapping(value = "catalog/sku/{sku}", produces = APPLICATION_JSON_VALUE)
    List<Product> getProductsBySku(@PathVariable(value = "sku") String sku) throws ServiceUnavailableException;

    @GetMapping(value = "/catalog/{ids}", produces = APPLICATION_JSON_VALUE)
    List<Product> getProductsByIds(@PathVariable(value = "ids") Set<String> ids);

}
