package com.microsoft.azure.productservice.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import com.microsoft.azure.productservice.model.Product;
import com.microsoft.azure.productservice.services.ProductService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    ProductService productService;

    @GET
    @Path("/{productId}")
    public Response getProduct(@PathParam("productId") int productId) {
        return productService.getProductById(productId)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getProducts() {
        return Response.ok(productService.getAllProducts()).build();
    }

    @POST
    public Response addProduct(Product product) {
        return Response.status(Response.Status.CREATED).entity(productService.addProduct(product)).build();
    }

    @PUT
    public Response updateProduct(Product product) {
        return productService.updateProduct(product)
                .map(updatedProduct -> Response.ok(updatedProduct).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{productId}")
    public Response deleteProduct(@PathParam("productId") int productId) {
        return productService.deleteProduct(productId)
            ? Response.noContent().build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }
}
