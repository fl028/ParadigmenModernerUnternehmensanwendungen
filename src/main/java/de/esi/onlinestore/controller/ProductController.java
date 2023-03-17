package de.esi.onlinestore.controller;

import de.esi.onlinestore.businesslogic.ProductBusinessService;
import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductBusinessService productBusinessService;

    public ProductController(ProductService productService) {
        this.productBusinessService = new ProductBusinessService(productService);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllproducts() {

        return ResponseEntity.ok(productBusinessService.getAll());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws BadRequestException {
        return ResponseEntity.ok(productBusinessService.create((product)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productBusinessService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id,@Valid @RequestBody  Product product) throws BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(productBusinessService.update(id,product));
    }

    @PutMapping
    public ResponseEntity<Product> updateProductById(@RequestBody  Product product) throws  BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(productBusinessService.update(product.getId(),product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)  throws ResourceNotFoundException {
        productBusinessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
