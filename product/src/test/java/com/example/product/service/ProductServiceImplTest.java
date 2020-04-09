package com.example.product.service;

import com.example.product.client.CatalogClient;
import com.example.product.client.InventoryClient;
import com.example.product.model.ApplicationException;
import com.example.product.model.Product;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final String PRODUCT_ID_1 = "ID_1";
    private static final String PRODUCT_ID_2 = "ID_2";
    private static final String PRODUCT_SKU = "SKU";
    private static final Product PRODUCT_1 = createProduct(PRODUCT_ID_1);
    private static final Product PRODUCT_2 = createProduct(PRODUCT_ID_2);

    @InjectMocks
    @Spy
    private ProductServiceImpl productService;

    @Mock
    private CatalogClient catalogClient;

    @Mock
    private InventoryClient inventoryClient;

    @Test
    void testGetProductsByIds() {
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);
        List<Product> products = ImmutableList.of(PRODUCT_1, PRODUCT_2);
        Map<String, Boolean> availabilities = ImmutableMap.of(PRODUCT_ID_1, true, PRODUCT_ID_2, false);
        when(catalogClient.getProductsByIds(ids)).thenReturn(products);
        when(inventoryClient.getAvailabilities(ids)).thenReturn(availabilities);

        List<Product> result = productService.getProductsByIds(ids);

        assertEquals(1, result.size());
        assertEquals(PRODUCT_1, result.get(0));
        verify(catalogClient).getProductsByIds(ids);
        verify(inventoryClient).getAvailabilities(ids);
    }

    @Test
    void testGetProductsByIdsCatalogFeignException() {
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);

        when(catalogClient.getProductsByIds(ids)).thenThrow(new FeignException.ServiceUnavailable("exception happened", Request.create(Request.HttpMethod.GET, "url", Collections.emptyMap(), null), null));

        try {
            productService.getProductsByIds(ids);
        } catch (Exception e) {}

        assertThrows(FeignException.class, () -> catalogClient.getProductsByIds(ids));
        verify(inventoryClient, never()).getAvailabilities(ids);
    }

    @Test
    void testGetProductsByIdsCatalogException() {
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);

        when(catalogClient.getProductsByIds(ids)).thenThrow(new MockitoException("exception happened"));

        try {
            productService.getProductsByIds(ids);
        } catch (Exception e) {}

        assertThrows(Exception.class, () -> catalogClient.getProductsByIds(ids));
        verify(inventoryClient, never()).getAvailabilities(ids);
    }

    @Test
    void testGetProductsByIdsInventoryFeignException() {
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);

        when(inventoryClient.getAvailabilities(ids)).thenThrow(new FeignException.ServiceUnavailable("exception happened", Request.create(Request.HttpMethod.GET, "url", Collections.emptyMap(), null), null));

        try {
            productService.getProductsByIds(ids);
        } catch (Exception e) {}

        assertThrows(FeignException.class, () -> inventoryClient.getAvailabilities(ids));
        verify(catalogClient).getProductsByIds(ids);
        verify(inventoryClient, atLeastOnce()).getAvailabilities(ids);
    }


    @Test
    void testGetProductsByIdsInventoryException() {
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);

        when(inventoryClient.getAvailabilities(ids)).thenThrow(new MockitoException("exception happened"));

        try {
            productService.getProductsByIds(ids);
        } catch (Exception e) {}

        assertThrows(Exception.class, () -> inventoryClient.getAvailabilities(ids));
        verify(catalogClient).getProductsByIds(ids);
        verify(inventoryClient, atLeastOnce()).getAvailabilities(ids);
    }

    @Test
    void testGetProductsBySku() {
        List<Product> products = ImmutableList.of(PRODUCT_1, PRODUCT_2);
        Map<String, Boolean> availabilities = ImmutableMap.of(PRODUCT_ID_1, true, PRODUCT_ID_2, false);
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);
        when(catalogClient.getProductsBySku(PRODUCT_SKU)).thenReturn(products);
        when(inventoryClient.getAvailabilities(ids)).thenReturn(availabilities);

        List<Product> result = productService.getProductsBySku(PRODUCT_SKU);

        assertEquals(1, result.size());
        assertEquals(PRODUCT_1, result.get(0));
        verify(catalogClient).getProductsBySku(PRODUCT_SKU);
        verify(inventoryClient, atLeastOnce()).getAvailabilities(ids);
    }

    private static Product createProduct(String id) {
        Product product = new Product();
        product.setId(id);
        product.setSku(PRODUCT_SKU);
        return product;
    }


}
