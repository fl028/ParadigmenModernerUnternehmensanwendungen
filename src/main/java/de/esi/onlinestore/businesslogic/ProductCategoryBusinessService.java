package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.ProductCategoryService;

import java.util.List;
import java.util.Optional;

public class ProductCategoryBusinessService {

    private final String ENTITY_NAME = "Productcategories";

    private final ProductCategoryService productCategoryService;

    private final ProductBusinessService productBusinessService;


    public ProductCategoryBusinessService(ProductCategoryService productCategoryService,ProductBusinessService productBusinessService) {
        this.productCategoryService = productCategoryService;
        this.productBusinessService = productBusinessService;
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

    public ProductCategory create(ProductCategory productCategory) throws BadRequestException, ResourceNotFoundException {
        if (productCategory.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }
        setCategoryForProducts(productCategory);
        return productCategoryService.save(productCategory);
    }

    private void setCategoryForProducts(ProductCategory productCategory) throws BadRequestException, ResourceNotFoundException {
        if (productCategory != null){
            for (Product product : productCategory.getProducts()) {
                product.setProductCategory(productCategory);
                productBusinessService.update(product.getId(),product);
            }
        }
    }

    public ProductCategory update(Long id, ProductCategory productCategory) throws BadRequestException, ResourceNotFoundException {
        if (id == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<ProductCategory> searchProductCategory = productCategoryService.findOne(id);
        if(searchProductCategory.isPresent()) {
            productCategory.setId(id);
            setCategoryForProducts(productCategory);
            return productCategoryService.save(productCategory);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
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
