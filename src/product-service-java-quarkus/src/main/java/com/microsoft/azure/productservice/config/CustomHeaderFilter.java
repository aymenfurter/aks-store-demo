package com.microsoft.azure.productservice.config;

import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomHeaderFilter implements ContainerResponseFilter {

    @Override
    public void filter(jakarta.ws.rs.container.ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("X-Version", "0.2");
    }
}
