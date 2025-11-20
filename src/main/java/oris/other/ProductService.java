package oris.other;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProducts();
    ProductEntity getProductById(Long id);
}