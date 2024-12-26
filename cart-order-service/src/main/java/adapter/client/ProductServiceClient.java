package adapter.client;

import domain.model.ProductInfo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "product-service")
public interface ProductServiceClient{

    @GET
    @Path("/products/{id}")
    ProductInfo getProduct(@PathParam("id") String productId);
}

