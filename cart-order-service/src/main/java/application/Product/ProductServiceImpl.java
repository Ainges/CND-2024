package application.Product;

import domain.Product;
import infrastructure.client.ProductApiClient;
import infrastructure.repository.Product.ProductRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProductServiceImpl implements ProductService{

    @Inject
    ProductRepository productRepository;


    @Inject
    @RestClient
    ProductApiClient productApiClient;


    /**
     * Checks if a product is known.
     *
     * @param productId the id of the product
     * @return true if the product is known, false otherwise
     */
    public boolean isProductknown(long productId) {

        Product product = new Product();

        try {
            product = productRepository.getProductById(productId);
            return product != null;
        }
        catch (Exception e) {
            return false;
        }


    }

    public Product getProductFormExternal(long productId) {
        Product product = new Product();

        // Get product from external service
        product = productApiClient.getProduct(productId);
        product = createProduct(product);

        return product;

    }

    public Product createProduct(Product product) {
        productRepository.persist(product);
        if(product.getId() == null) {
            throw new ProductServiceException("Could not create Product", null);
        }
        return product;
    }
}
