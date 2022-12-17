package itp.instituto.product.service;

import itp.instituto.product.entity.Category;
import itp.instituto.product.entity.Product;

import java.util.List;

public interface ProductService {
    //retorna una lista con todos los productos
    public List<Product> listAllProduct();

    //recuperar un producto pasandole la id
    public Product getProduct(Long id);

    //Crear un producto
    public Product createProduct(Product product);

    //Actualiza un producto
    public Product updateProduct(Product product);

    //borrar un  producto
    //public Product deleteProduct(Long id);
    public Product deleteProduct(Long id, Boolean eliminar);

    //recuperar un conjunto de productos de una categoria especifica
    public List<Product> findByCategory(Category category);

    //Actualizar el stock, a un producto necesitamos el id, cantidad
    public Product updateStock(Long id, Double quantity);


}
