package com.example.product.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "${inventory.application.name}")
@RibbonClient(name = "${inventory.application.name}")
public interface InventoryClient {

    @PostMapping(value = "/availabilities", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Boolean> getAvailabilities(@RequestBody Set<String> ids);

}
