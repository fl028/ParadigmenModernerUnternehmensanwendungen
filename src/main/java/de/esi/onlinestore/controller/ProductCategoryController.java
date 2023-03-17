package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productcategories")
public class ProductCategoryController {

    private final String ENTITY_NAME = "Productcategories";

    private final ProductCategoryService productcategoriesService;

    public ProductCategoryController(ProductCategoryService productcategoriesService) {
        this.productcategoriesService = productcategoriesService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllproductcategorys() {
        List<ProductCategory> result= productcategoriesService.findAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productcategory) throws BadRequestException {
        if (productcategory.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        ProductCategory result = productcategoriesService.save(productcategory);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(id);
        if(searchProductCategory.isPresent()) {
            return ResponseEntity.ok(searchProductCategory.get());
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategoryById(@PathVariable(value = "id") Long id,@Valid @RequestBody ProductCategory productcategory) throws ResourceNotFoundException,InternalError {
        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(id);
        if(searchProductCategory.isPresent()) {
            productcategory.setId(id);
            ProductCategory result = productcategoriesService.save(productcategory);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + productcategory.getId());
        }
    }

    @PutMapping
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productcategory) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (productcategory.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(productcategory.getId());
        if(searchProductCategory.isPresent()) {
            try {
                ProductCategory result = productcategoriesService.save(productcategory);
                return ResponseEntity.ok(result);
            } catch (Exception e) {
                throw new InternalError();
            }
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + productcategory.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(id);
        if(searchProductCategory.isPresent()) {
            productcategoriesService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }
}
