package com.microsoft.azure;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.productservice.model.Product;
import com.microsoft.azure.productservice.services.ProductService;

import jakarta.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ProductServiceApplicationTests {

    @Inject
    ProductService productService;

    private Product sampleProduct;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        sampleProduct = new Product(1, "Sample Product", 100f, "Sample description", "sample.png");
        productService.addProduct(sampleProduct);  
    }

    @Test
    public void testHealthCheck() {
        given()
          .when().get("/q/health")
          .then()
             .statusCode(200)
             .body("status", is("UP"));
    }

    @Test
    public void testCorsConfiguration() {
        given()
          .header("Origin", "http://example.com")
          .when().get("/")
          .then()
             .statusCode(200)
             .header("Access-Control-Allow-Origin", "http://example.com");
    }

    @Test
    public void testXVersionHeader() {
        given()
          .when().get("/")
          .then()
             .statusCode(200)
             .header("X-Version", "0.2");
    }

    @Test
    public void testGetProduct() {
        given()
          .when().get("/{productId}", 1)
          .then()
             .statusCode(200)
             .body("id", is(1));
    }

    @Test
    public void testGetProducts() {
        given()
          .when().get("/")
          .then()
             .statusCode(200)
             .body("[0].id", is(1));
    }

    @Test
    public void testAddProduct() throws Exception {
        Product newProduct = new Product(2, "New Product", 200f, "New description", "new.png");

        given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(objectMapper.writeValueAsString(newProduct))
          .when().post("/")
          .then()
             .statusCode(201)
             .body("name", is("New Product"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        sampleProduct = new Product(1, "Updated Product", 200f, "Updated description", "updated.png");

        given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(objectMapper.writeValueAsString(sampleProduct))
          .when().put("/")
          .then()
             .statusCode(200)
             .body("name", is("Updated Product"));
    }

    @Test
    public void testDeleteProduct() {
        given()
          .when().delete("/{productId}", 1)
          .then()
             .statusCode(204);
    }

    @Test
    public void testDeleteProductNotFound() {
        given()
          .when().delete("/{productId}", 99)
          .then()
             .statusCode(404);
    }
}
