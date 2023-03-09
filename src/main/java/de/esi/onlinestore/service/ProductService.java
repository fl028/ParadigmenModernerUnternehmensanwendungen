package de.esi.onlinestore.service;


import de.esi.onlinestore.domain.Product;
import de.esi.onlinestore.repository.ProductRepository;
import de.esi.onlinestore.interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        logger.debug("Request to save Product {}", product);
        return productRepository.save(product);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Product {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        logger.debug("Request to get all Product");
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findOne(Long id) {
        logger.debug("Request to find Product {}", id);
        return productRepository.findById(id);
    }
}
