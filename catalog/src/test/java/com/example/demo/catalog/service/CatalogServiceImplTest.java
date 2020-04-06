package com.example.demo.catalog.service;

import com.example.demo.catalog.dao.CatalogLoader;
import com.example.demo.catalog.model.Product;
import com.example.demo.catalog.model.ProductDto;
import com.example.demo.catalog.utils.ProductConverter;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogServiceImplTest {

    private static final String PRODUCT_ID_1 = "1";
    private static final String PRODUCT_ID_2 = "2";
    private static final String PRODUCT_ID_3 = "3";
    private static final String PRODUCT_ID_4 = "4";
    private static final String SKU_1 = "SKU_1";
    private static final String SKU_2 = "SKU_2";


    private static final Product PRODUCT_1 = createProduct(PRODUCT_ID_1, SKU_1);
    private static final Product PRODUCT_2 = createProduct(PRODUCT_ID_2, SKU_1);
    private static final Product PRODUCT_3 = createProduct(PRODUCT_ID_3, SKU_2);
    private static final Product PRODUCT_4 = createProduct(PRODUCT_ID_4, SKU_2);
    private static final List<Product> PRODUCTS = Arrays.asList(PRODUCT_1, PRODUCT_2, PRODUCT_3, PRODUCT_4);
    private static final List<ProductDto> PRODUCT_DTOS = PRODUCTS.stream().map(ProductConverter::convert).collect(Collectors.toList());

    @InjectMocks
    CatalogServiceImpl catalogService;

    @Mock
    CatalogLoader catalogLoader;


    @BeforeEach
    void setup() throws IOException {

        when(catalogLoader.getProducts()).thenReturn(PRODUCTS);

        catalogService.loadAllProducts();
    }

    @Test
    void testGetProductsByIdsEmpty() {

        List<ProductDto> result = catalogService.getProductsByIds(null);

        assertEquals(PRODUCT_DTOS, result);
    }

    @Test
    void testGetProductsByIds() {
        Set<String> queryIds = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_3);
        List<ProductDto> result = catalogService.getProductsByIds(queryIds);

        assertEquals(2, result.size());
        assertTrue(result.contains(ProductConverter.convert(PRODUCT_1)));
        assertTrue(result.contains(ProductConverter.convert(PRODUCT_3)));
    }

    @Test
    void testGetProductsBySku() {
        List<ProductDto> result = catalogService.getProductsBySku(SKU_2);

        assertEquals(2, result.size());
        assertTrue(result.contains(ProductConverter.convert(PRODUCT_3)));
        assertTrue(result.contains(ProductConverter.convert(PRODUCT_4)));
    }

    private static Product createProduct(String id, String sku) {
        Product product = new Product();
        product.setId(id);
        product.setSku(sku);
        return product;
    }

}
