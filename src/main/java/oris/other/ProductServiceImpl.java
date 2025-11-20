package oris.other;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    @Override
    public List<ProductEntity> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productDao.findById(id);
    }
}