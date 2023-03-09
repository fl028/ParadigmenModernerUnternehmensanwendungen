package de.esi.onlinestore.service;


import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.interfaces.IOrderItemService;
import de.esi.onlinestore.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemService implements IOrderItemService {

    Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    private final OrderItemRepository orderitemRepository;

    public OrderItemService(OrderItemRepository orderitemRepository) {
        this.orderitemRepository = orderitemRepository;
    }

    @Override
    public OrderItem save(OrderItem orderitem) {
        logger.debug("Request to save OrderItem {}", orderitem);
        return orderitemRepository.save(orderitem);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete OrderItem {}", id);
        orderitemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        logger.debug("Request to get all OrderItem");
        return orderitemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> findOne(Long id) {
        logger.debug("Request to find OrderItem {}", id);
        return orderitemRepository.findById(id);
    }
}
