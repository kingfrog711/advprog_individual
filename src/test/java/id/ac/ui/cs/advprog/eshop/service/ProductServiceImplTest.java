package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    ProductServiceImpl productService; // We will instantiate this manually

    Product product;

    @BeforeEach
    void setUp() {
        // Manual construction using the Mock object
        productService = new ProductServiceImpl(productRepository);

        product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);

        assertEquals(product.getProductId(), created.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        when(productRepository.update(product)).thenReturn(product);

        productService.update(product);

        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        String productId = "test-id";
        doNothing().when(productRepository).delete(productId);

        productService.delete(productId);

        verify(productRepository, times(1)).delete(productId);
    }
}