package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.OrderItemService;

import java.util.List;
import java.util.Optional;

public class OrderItemBusinessService {

    private final String ENTITY_NAME = "OrderItem";

    private final OrderItemService orderItemService;

    public OrderItemBusinessService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    public List<OrderItem> getAll() {
        return  orderItemService.findAll();
    }

    public OrderItem getOne(Long id) throws ResourceNotFoundException {
        Optional<OrderItem> searchOrderItem = orderItemService.findOne(id);
        if(searchOrderItem.isPresent()) {
            return searchOrderItem.get();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    public OrderItem create(OrderItem orderItem) throws BadRequestException {
        if (orderItem.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        return orderItemService.save(orderItem);
    }

    public OrderItem update(Long id, OrderItem orderItem) throws BadRequestException, ResourceNotFoundException {
        if (orderItem.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<OrderItem> searchOrderItem = orderItemService.findOne(id);
        if(searchOrderItem.isPresent()) {
            orderItem.setId(id);
            return  orderItemService.save(orderItem);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + orderItem.getId());
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<OrderItem> searchOrderItem = orderItemService.findOne(id);
        if(searchOrderItem.isPresent()) {
            orderItemService.delete(id);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }



}
