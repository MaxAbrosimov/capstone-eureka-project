package com.example.inventory.service;

import com.example.inventory.client.CatalogClient;
import com.example.inventory.model.Product;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceImplTest {

    private static final String PRODUCT_1_ID = "id_1";
    private static final String PRODUCT_2_ID = "id_2";
    private static final Product PRODUCT_1 = createProduct(PRODUCT_1_ID);
    private static final Product PRODUCT_2 = createProduct(PRODUCT_2_ID);

    @Mock
    private CatalogClient client;

    @InjectMocks
    private AvailabilityServiceImpl service;

    @BeforeEach
    void setup() {
        List<Product> products = ImmutableList.of(PRODUCT_1, PRODUCT_2);
        when(client.getAll()).thenReturn(products);

        service.fillAvailabilities();
    }

    @Test
    void testGetProductAvailabilities() {
        Map<String, Boolean> result = service.getProductAvailabilities(Collections.singleton(PRODUCT_1_ID));

        assertTrue(result.containsKey(PRODUCT_1_ID));
    }

    private static Product createProduct(String id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

}
