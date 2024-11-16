package infrastructure.client;

import domain.Product;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "product-api")
@Path("/product")
public interface ProductApiClient {

    @GET
    @Path("{id}")
    Product getProduct(@PathParam("id") long productId);


}
