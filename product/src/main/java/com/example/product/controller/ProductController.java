package com.example.product.controller;

import com.example.product.model.ApplicationException;
import com.example.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Set;

@RestController
@RequestMapping(value = "/products/")
public class ProductController {
    private static Logger log = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Response getByIds(@RequestBody Set<String> ids) {
        log.info("Load products by ids {}", ids);
        return Response.ok(productService.getProductsByIds(ids)).build();
    }

    @GetMapping(value = "sku/{sku}")
    public Response getBySku(@PathVariable String sku) {
        log.info("Load products by sku {}", sku);
        return Response.ok(productService.getProductsBySku(sku)).build();
    }

    @ExceptionHandler(ApplicationException.class)
    public Response handleCustomException(ApplicationException ex) {
        log.error("Error was happened {}", ex.getMessage(), ex.getCause());
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(ex.getMessage()).build();
    }

}
