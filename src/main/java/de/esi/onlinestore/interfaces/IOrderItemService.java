package de.esi.onlinestore.interfaces;

import de.esi.onlinestore.domain.OrderItem;

import java.util.List;
import java.util.Optional;

public interface IOrderItemService {

    public OrderItem save(OrderItem orderitem);

    public void delete(Long id);

    public List<OrderItem> findAll();

    public Optional<OrderItem> findOne(Long id);
}
