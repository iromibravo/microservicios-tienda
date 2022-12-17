package itp.instituto.product.service;

import itp.instituto.product.entity.Category;
import itp.instituto.product.entity.Product;
import itp.instituto.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);

    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreatedAt(new Date());
        //guardar el producto en la bd
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if (productDB == null) {
            return null;
        }
        //si el producto exite con una id
        productDB.setName(product.getName());
        productDB.setDescription(productDB.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id, Boolean eliminar) {
        Product productDB = getProduct(id);
        if (productDB == null) {
            return null;
        }
        if(eliminar==false) {
            productDB.setStatus("DELETED");
            return productRepository.save(productDB);
        }
        else{
            productRepository.delete(productDB);
            return productDB;
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if (productDB == null) {
            return null;
        }
        Double stock = productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
}
