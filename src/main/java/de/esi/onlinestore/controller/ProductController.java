package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final String ENTITY_NAME = "Product";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. Liste aller Produkte aufrufen GET /products
    @GetMapping
    public ResponseEntity<List<Product>> getAllproducts() {
        List<Product> result= productService.findAll();
        return ResponseEntity.ok(result);
    }

    // 2. Neues Produkt erstellen POST /products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws BadRequestException {
        if (product.getId() != null) {
            String message = "A new product cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Product result = productService.save(product);
        return ResponseEntity.ok(result);
    }

    // 3. Produtk aufrufen (Parameter: Id) GET /products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Product> product = productService.findOne(id);
        if(product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        else{
            throw new ResourceNotFoundException("No product with id: " + id);
        }
    }

    // 4. Produkt überschreiben (Parameter: Id) PUT /products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id,@Valid @RequestBody  Product product) throws BadRequestException,ResourceNotFoundException,InternalError {
        Optional<Product> searchProduct = productService.findOne(id);
        if(searchProduct.isPresent()) {
            Product result = productService.save(product);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No product with id: " + product.getId());
        }
    }

    // 5. Produkt überschreiben (Parameter: Instanz von Produkt mit aktuellen Werten PUT /products
    @PutMapping
    public ResponseEntity<Product> updateProductById(@RequestBody  Product product) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (product.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Optional<Product> searchProduct = productService.findOne(product.getId());
        if(searchProduct.isPresent()) {
            Product result = productService.save(product);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No product with id: " + product.getId());
        }
    }

    // 6. Produkt löschen (Parameter: Id) DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<Product> searchProduct = productService.findOne(id);
        if(searchProduct.isPresent()) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No product with id: " + id);
        }
    }
}