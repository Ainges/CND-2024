package application.Product;

import domain.Product;

public interface ProductService {

    boolean isProductknown(long productId);
    Product getProductFormExternal(long productId);

}
