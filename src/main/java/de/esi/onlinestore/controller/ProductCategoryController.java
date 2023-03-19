package de.esi.onlinestore.controller;

import de.esi.onlinestore.businesslogic.CustomerBusinessService;
import de.esi.onlinestore.businesslogic.ProductBusinessService;
import de.esi.onlinestore.businesslogic.ProductCategoryBusinessService;
import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.DuplicateEmailException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.CustomerService;
import de.esi.onlinestore.service.ProductCategoryService;
import de.esi.onlinestore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productcategories")
public class ProductCategoryController {

    private final ProductCategoryBusinessService productCategoryBusinessService;

    public ProductCategoryController(ProductCategoryService productCategoryService,ProductService productService) {

        this.productCategoryBusinessService = new ProductCategoryBusinessService(productCategoryService,new ProductBusinessService(productService));
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllproductcategorys() {
        return ResponseEntity.ok(productCategoryBusinessService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@Valid @RequestBody ProductCategory productcategory) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(productCategoryBusinessService.create(productcategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productCategoryBusinessService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategoryById(@PathVariable(value = "id") Long id,@Valid @RequestBody ProductCategory productcategory) throws ResourceNotFoundException, InternalError, BadRequestException {
        return ResponseEntity.ok(productCategoryBusinessService.update(id,productcategory));
    }

    @PutMapping
    public ResponseEntity<ProductCategory> updateProductCategory(@Valid @RequestBody ProductCategory productcategory) throws  BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(productCategoryBusinessService.update(productcategory.getId(),productcategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id)  throws ResourceNotFoundException {
        productCategoryBusinessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
