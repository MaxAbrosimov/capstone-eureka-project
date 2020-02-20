package com.example.inventory.service;

import java.util.Map;
import java.util.Set;

public interface AvailabilityService {

    Map<String, Boolean> getProductAvailabilities(Set<String> ids);

}
