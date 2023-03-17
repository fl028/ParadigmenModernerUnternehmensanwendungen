package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.ProductCategoryService;

import java.util.List;
import java.util.Optional;

public class ProductCategoryBusinessService {

    private final String ENTITY_NAME = "Productcategories";

    private final ProductCategoryService productCategoryService;

    public ProductCategoryBusinessService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    public List<ProductCategory> getAll() {
        return productCategoryService.findAll();
    }

    public ProductCategory getOne(Long id) throws ResourceNotFoundException {
        Optional<ProductCategory> searchProductCategory = productCategoryService.findOne(id);
        if(searchProductCategory.isPresent()) {
            return searchProductCategory.get();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    public ProductCategory create(ProductCategory productCategory) throws BadRequestException {
        if (productCategory.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        return productCategoryService.save(productCategory);
    }

    public ProductCategory update(Long id, ProductCategory productCategory) throws BadRequestException, ResourceNotFoundException {
        if (productCategory.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<ProductCategory> searchProductCategory = productCategoryService.findOne(id);
        if(searchProductCategory.isPresent()) {
            productCategory.setId(id);
            return productCategoryService.save(productCategory);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + productCategory.getId());
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<ProductCategory> searchProductCategory = productCategoryService.findOne(id);
        if(searchProductCategory.isPresent()) {
            productCategoryService.delete(id);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }
}
