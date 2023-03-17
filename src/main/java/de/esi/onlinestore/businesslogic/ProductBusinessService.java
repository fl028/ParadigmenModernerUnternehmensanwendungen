package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductBusinessService {

    private final String ENTITY_NAME = "Product";

    private final ProductService productService;

    public ProductBusinessService(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAll() {
        return  productService.findAll();
    }

    public Product getOne(Long id) throws ResourceNotFoundException {
        Optional<Product> searchProduct = productService.findOne(id);
        if(searchProduct.isPresent()) {
            return searchProduct.get();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    public Product create(Product product) throws BadRequestException {

        if (product.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        return productService.save(product);
    }

    public Product update(Long id, Product product) throws BadRequestException, ResourceNotFoundException {
        if (product.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<Product> searchProduct = productService.findOne(id);
        if(searchProduct.isPresent()) {
            product.setId(id);
            return  productService.save(product);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + product.getId());
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Product> searchProduct = productService.findOne(id);
        if(searchProduct.isPresent()) {
            productService.delete(id);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }



}
