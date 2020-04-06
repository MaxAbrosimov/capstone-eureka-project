package com.example.demo.catalog.controller;

import com.example.demo.catalog.model.ProductDto;
import com.example.demo.catalog.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogControllerTest {

    @InjectMocks
    CatalogController catalogController;

    @Mock
    CatalogService catalogService;


    @Test
    void testGetProductsByIds() {
        //given
        String productId = "1";
        Set<String> productIds = Collections.singleton(productId);

        ProductDto dto = new ProductDto();
        dto.setId(productId);

        List<ProductDto> productDtos = Collections.singletonList(dto);

        //when
        when(catalogService.getProductsByIds(productIds)).thenReturn(productDtos);

        //then
        List<ProductDto> result = catalogController.getProductsByIds(productIds);

        assertEquals(productDtos, result);
        verify(catalogService).getProductsByIds(productIds);
    }

    @Test
    void testGetProductsBySku() {
        //given
        String sku = "1";
        ProductDto dto = new ProductDto();
        dto.setSku(sku);
        List<ProductDto> productDtos = Collections.singletonList(dto);

        //when
        when(catalogService.getProductsBySku(sku)).thenReturn(productDtos);

        //then
        List<ProductDto> result = catalogController.getProductsBySku(sku);

        assertEquals(productDtos, result);
        verify(catalogService).getProductsBySku(sku);
    }

    @Test
    void testGetAll() {
        //given
        ProductDto dto = new ProductDto();
        List<ProductDto> productDtos = Collections.singletonList(dto);

        //when
        when(catalogService.getProductsByIds(null)).thenReturn(productDtos);

        //then
        List<ProductDto> result = catalogController.getAll();

        assertEquals(productDtos, result);
        verify(catalogService).getProductsByIds(null);
    }

}
