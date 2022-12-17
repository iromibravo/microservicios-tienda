package itp.instituto.product.controller;

import itp.instituto.product.entity.Category;
import itp.instituto.product.entity.Product;
import itp.instituto.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping (value="/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //metodo listar productos
    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name="categoryId",required=false) Long categoryId){
        List<Product> products = new ArrayList<>();
        if (categoryId==null) {
            products = productService.listAllProduct();
        }
        else{
            Category category = Category.builder().id(categoryId).build();
            products = productService.findByCategory(category);
        }
        if(products.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    //devolver un producto por su id
    @GetMapping(value="/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        if(product==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    //crear el metodo Post, en este caso crear un nuevo producto
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
        }
        Product productRes = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRes);

    }

    //Actualizar un producto Update, el producto debe existir
    @PutMapping(value="/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        //traemos de la bd el producto con ese id
        Product productDB = productService.getProduct(id);
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());
        productDB.setStatus(product.getStatus());
        productDB.setStock(product.getStock());
        productDB.setName(product.getName());

        productService.updateProduct(productDB);
        if(productDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }


    @DeleteMapping(value="/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id, @RequestParam(name="eliminar", required = true) Boolean eliminar){
            Product productDelete = productService.deleteProduct(id,eliminar);
        if(productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @PutMapping(value="/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable Long id, @RequestParam(name="quantity", required = true) Double quantity){
        Product product = productService.updateStock(id, quantity);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

}
