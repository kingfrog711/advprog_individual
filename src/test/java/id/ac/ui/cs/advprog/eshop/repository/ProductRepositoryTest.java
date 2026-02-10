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
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9896");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId("id-123");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedData = new Product();
        updatedData.setProductId("id-123");
        updatedData.setProductName("New Name");
        updatedData.setProductQuantity(20);
        Product result = productRepository.update(updatedData);

        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditProductNegative_NotFound() {
        Product nonExistent = new Product();
        nonExistent.setProductId("ghost-id");
        nonExistent.setProductName("Ghost");
        nonExistent.setProductQuantity(0);
        Product result = productRepository.update(nonExistent);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("id-delete-me");
        product.setProductName("To Be Deleted");
        product.setProductQuantity(5);
        productRepository.create(product);

        productRepository.delete("id-delete-me");

        assertNull(productRepository.findById("id-delete-me"));
        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProductNegative_NotFound() {
        Product product = new Product();
        product.setProductId("id-keep-me");
        productRepository.create(product);

        productRepository.delete("random-id");

        assertNotNull(productRepository.findById("id-keep-me"));
    }
}