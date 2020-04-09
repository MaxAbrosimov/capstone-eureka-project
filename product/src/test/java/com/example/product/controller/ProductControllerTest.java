package com.example.product.controller;

import com.example.product.model.ApplicationException;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private static final String PRODUCT_ID_1 = "ID_1";
    private static final String PRODUCT_ID_2 = "ID_2";
    private static final Product PRODUCT_1 = createProduct(PRODUCT_ID_1);
    private static final Product PRODUCT_2 = createProduct(PRODUCT_ID_2);

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductService productService;

    @Test
    void testHandleCustomException() {
        String exceptionMessage = "Exception happened";

        Response response = controller.handleCustomException(new ApplicationException(exceptionMessage, new Exception()));

        assertEquals(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), response.getStatus());
        assertEquals(exceptionMessage, response.getEntity().toString());
    }

    @Test
    void testGetByIds() throws ApplicationException {
        Set<String> ids = ImmutableSet.of(PRODUCT_ID_1, PRODUCT_ID_2);

        List<Product> products = ImmutableList.of(PRODUCT_1, PRODUCT_2);


        when(productService.getProductsByIds(ids)).thenReturn(products);
        Response response = controller.getByIds(ids);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(products, response.getEntity());
    }

    @Test
    void testGetBySku() {
        String sku = "some_sku";

        List<Product> products = ImmutableList.of(PRODUCT_1, PRODUCT_2);


        when(productService.getProductsBySku(sku)).thenReturn(products);
        Response response = controller.getBySku(sku);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(products, response.getEntity());
    }

    private static Product createProduct(String id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

}
