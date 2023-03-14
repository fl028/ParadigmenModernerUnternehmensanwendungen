package de.esi.onlinestore.controller;

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

    // 1. Liste aller Produktkategorien aufrufen GET /productcategories
    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllproductcategorys() {
        List<ProductCategory> result= productcategoriesService.findAll();
        return ResponseEntity.ok(result);
    }

    // 2. Neue Produktkategorie erstellen POST /productcategories
    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productcategory) throws BadRequestException {
        if (productcategory.getId() != null) {

            String message = "A new productcategory cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        ProductCategory result = productcategoriesService.save(productcategory);
        return ResponseEntity.ok(result);
    }

    // 3. Produtkategorie aufrufen (Parameter: Id) GET /productcategories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<ProductCategory> productcategory = productcategoriesService.findOne(id);
        if(productcategory.isPresent()) {
            return ResponseEntity.ok(productcategory.get());
        }
        else{
            throw new ResourceNotFoundException("No productcategory with id: " + id);
        }
    }

    // 4. Produktkategorie überschreiben (Parameter: Id) PUT /productcategories/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategoryById(@PathVariable(value = "id") Long id,@Valid @RequestBody ProductCategory productcategory) throws ResourceNotFoundException,InternalError {
        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(id);
        if(searchProductCategory.isPresent()) {
            ProductCategory result = productcategoriesService.save(productcategory);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No productcategory with id: " + productcategory.getId());
        }
    }

    // 5. Produktkategorie überschreiben (Parameter: Instanz von Produktkategorie mitaktuellen Werten) PUT /productcategories
    @PutMapping
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productcategory) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (productcategory.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(productcategory.getId());
        if(searchProductCategory.isPresent()) {
            ProductCategory result = productcategoriesService.save(productcategory);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No productcategory with id: " + productcategory.getId());
        }
    }

    // 6. Produktkategorie löschen (Parameter: Id) DELETE /productcategories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<ProductCategory> searchProductCategory = productcategoriesService.findOne(id);
        if(searchProductCategory.isPresent()) {
            productcategoriesService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No productcategory with id: " + id);
        }
    }
}
