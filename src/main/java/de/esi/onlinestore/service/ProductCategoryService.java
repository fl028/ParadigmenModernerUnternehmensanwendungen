package de.esi.onlinestore.service;


import de.esi.onlinestore.domain.ProductCategory;
import de.esi.onlinestore.interfaces.IProductCategoryService;
import de.esi.onlinestore.repository.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductCategoryService implements IProductCategoryService {

    Logger logger = LoggerFactory.getLogger(ProductCategoryService.class);

    private final ProductCategoryRepository productcategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productcategoryRepository) {
        this.productcategoryRepository = productcategoryRepository;
    }

    @Override
    public ProductCategory save(ProductCategory productcategory) {
        logger.debug("Request to save ProductCategory {}", productcategory);
        return productcategoryRepository.save(productcategory);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete ProductCategory {}", id);
        productcategoryRepository.deleteById(id);
    }

    @Override
    public List<ProductCategory> findAll() {
        logger.debug("Request to get all ProductCategory");
        return productcategoryRepository.findAll();
    }

    @Override
    public Optional<ProductCategory> findOne(Long id) {
        logger.debug("Request to find ProductCategory {}", id);
        return productcategoryRepository.findById(id);
    }
}
