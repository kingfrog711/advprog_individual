package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateWithId() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product found = productRepository.findById("123");
        assertEquals("123", found.getProductId());
    }

    @Test
    void testCreateWithoutId() {
        Product product = new Product();
        product.setProductName("Auto-ID Product");
        product.setProductQuantity(5);
        productRepository.create(product);

        assertNotNull(product.getProductId());
        assertFalse(product.getProductId().isEmpty());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("1");
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("2");
        productRepository.create(product2);

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals("1", iterator.next().getProductId());
        assertTrue(iterator.hasNext());
        assertEquals("2", iterator.next().getProductId());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product found = productRepository.findById("123");
        assertNotNull(found);
        assertEquals("123", found.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product found = productRepository.findById("456");
        assertNull(found);
    }

    @Test
    void testUpdateSuccess() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Old Name");
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("123");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());

        Product found = productRepository.findById("123");
        assertEquals(20, found.getProductQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("456");
        updatedProduct.setProductName("Non-existent");

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        productRepository.delete("123");
        Product found = productRepository.findById("123");
        assertNull(found);
    }

    @Test
    void testDeleteNotFound() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        productRepository.delete("456");
        Product found = productRepository.findById("123");
        assertNotNull(found);
    }
}