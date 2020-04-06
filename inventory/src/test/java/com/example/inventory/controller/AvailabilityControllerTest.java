package com.example.inventory.controller;

import com.example.inventory.service.AvailabilityService;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AvailabilityControllerTest {

    @InjectMocks
    private AvailabilityController controller;

    @Mock
    private AvailabilityService availabilityService;

    @Test
    void testGetAvailabilities() {
        String id = "1";
        Set<String> ids = Collections.singleton(id);

        controller.getAvailabilities(ids);

        verify(availabilityService).getProductAvailabilities(ids);
    }

}
