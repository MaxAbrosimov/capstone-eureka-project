package com.example.inventory.service;

import com.example.inventory.client.CatalogClient;
import com.example.inventory.model.Product;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private static Logger log = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

    private final CatalogClient client;

    private Map<String, Boolean> availabilities;

    @Autowired
    public AvailabilityServiceImpl(CatalogClient client) {
        this.client = client;
    }

    public Map<String, Boolean> getProductAvailabilities(Set<String> ids) {
        log.info("Loading availabilities by ids: {}", ids);
        Map<String, Boolean> result = availabilities.entrySet().stream().filter(entry -> ids.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        log.info("Loaded availabilities by ids: {}", result);
        return result;
    }

    @PostConstruct
    private void fillAvailabilities() {
        log.info("Processing availabilities on startup");
        availabilities = client.getAll().stream().collect(Collectors.toMap(Product::getId, entry-> RandomUtils.nextBoolean()));
        log.info("Processed availabilities: {}", availabilities);
    }

}
