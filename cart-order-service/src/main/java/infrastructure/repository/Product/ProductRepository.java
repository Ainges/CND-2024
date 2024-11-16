package infrastructure.repository.Product;

import domain.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    Logger logger = LoggerFactory.getLogger(ProductRepository.class);


    public Product getProductById(long id) {
        Product product =  find("id", id).firstResult();

        if(product == null) {
            logger.error("Product with id {} not found", id);
            throw new ProductRepositoryException("Product with id " + id + " not found");

        }
        return product;

    }

}
