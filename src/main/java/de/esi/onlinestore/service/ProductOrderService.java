package de.esi.onlinestore.service;


import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.interfaces.IProductOrderService;
import de.esi.onlinestore.repository.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductOrderService implements IProductOrderService {

    Logger logger = LoggerFactory.getLogger(ProductOrderService.class);

    private final ProductOrderRepository productorderRepository;

    public ProductOrderService(ProductOrderRepository productorderRepository) {
        this.productorderRepository = productorderRepository;
    }

    @Override
    public ProductOrder save(ProductOrder productorder) {
        logger.debug("Request to save ProductOrder {}", productorder);
        return productorderRepository.save(productorder);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete ProductOrder {}", id);
        productorderRepository.deleteById(id);
    }

    @Override
    public List<ProductOrder> findAll() {
        logger.debug("Request to get all ProductOrder");
        return productorderRepository.findAll();
    }

    @Override
    public Optional<ProductOrder> findOne(Long id) {
        logger.debug("Request to find ProductOrder {}", id);
        return productorderRepository.findById(id);
    }
}
