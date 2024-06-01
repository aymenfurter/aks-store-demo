package com.microsoft.azure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import com.microsoft.azure.productservice.model.Product;
import com.microsoft.azure.productservice.services.ProductService;

@QuarkusTest
public class ProductServiceTest {

    @Inject
    ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductService();
        productService.addProduct(new Product(1, "Captain Catnip's Fisherman's Friend", 9.99f, "Watch your feline friend embark on a fishing adventure with Captain Catnip's Fisherman's Friend toy. Packed with irresistible catnip and dangling fish lure.", "/placeholder.png"));
    }

    @Test
    public void testGetProductById() {
        Optional<Product> product = productService.getProductById(1);
        assertTrue(product.isPresent());
        assertEquals(1, product.get().id());
    }

    @Test
    public void testAddProduct() {
        Product product = productService.addProduct(new Product(0, "New Product", 50f, "New description", "new.png"));
        assertNotNull(product);
        assertEquals(12, productService.getAllProducts().size());
    }

    @Test
    public void testUpdateProduct() {
        Product updatedProduct = new Product(1, "Updated Product", 200f, "Updated description", "updated.png");
        Optional<Product> product = productService.updateProduct(updatedProduct);
        assertTrue(product.isPresent());
        assertEquals("Updated Product", product.get().name());
    }

    @Test
    public void testDeleteProduct() {
        assertTrue(productService.deleteProduct(1));
        assertFalse(productService.getProductById(1).isPresent());
    }

    @Test
    public void testAddProductWithSameId() {
        Product product1 = productService.addProduct(new Product(0, "Product1", 50f, "description1", "image1.png"));
        Product product2 = productService.addProduct(new Product(0, "Product2", 60f, "description2", "image2.png"));
        assertNotEquals(product1.id(), product2.id());
        assertEquals(13, productService.getAllProducts().size());
    }

    @Test
    public void testUpdateNonExistentProduct() {
        Product nonExistentProduct = new Product(99, "Non-Existent Product", 200f, "description", "image.png");
        Optional<Product> product = productService.updateProduct(nonExistentProduct);
        assertFalse(product.isPresent());
    }

    @Test
    public void testDeleteNonExistentProduct() {
        assertFalse(productService.deleteProduct(99));
    }
}
