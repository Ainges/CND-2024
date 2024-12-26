package adapter.client;

import domain.model.ProductInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProductServiceClientImpl implements ProductServiceClient {


    @Inject
    @RestClient
    ProductServiceClient productServiceClient;

    @Override
    public ProductInfo getProduct(String productId) {
        ProductInfo productInfo = productServiceClient.getProduct(productId);

        if (productInfo.getId() == null) {
            throw new ProductServiceClientException("Product not found");
        }

        return productInfo;
    }
}
