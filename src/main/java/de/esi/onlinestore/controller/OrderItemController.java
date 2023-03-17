package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {

    private final String ENTITY_NAME = "OrderItem";

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllorderitems() {
        List<OrderItem> result= orderItemService.findAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderitem) throws BadRequestException {
        if (orderitem.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        OrderItem result = orderItemService.save(orderitem);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<OrderItem> searchOrderItem = orderItemService.findOne(id);
        if(searchOrderItem.isPresent()) {
            return ResponseEntity.ok(searchOrderItem.get());
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable(value = "id") Long id,@Valid @RequestBody  OrderItem orderitem) throws BadRequestException,ResourceNotFoundException,InternalError {
        Optional<OrderItem> searchOrderItem = orderItemService.findOne(id);
        if(searchOrderItem.isPresent()) {
            OrderItem result = orderItemService.save(orderitem);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + orderitem.getId());
        }
    }

    @PutMapping
    public ResponseEntity<OrderItem> updateOrderItemById(@RequestBody OrderItem orderitem) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (orderitem.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<OrderItem> searchOrderItem = orderItemService.findOne(orderitem.getId());
        if(searchOrderItem.isPresent()) {
            OrderItem result = orderItemService.save(orderitem);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + orderitem.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<OrderItem> searchOrderItem = orderItemService.findOne(id);
        if(searchOrderItem.isPresent()) {
            orderItemService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }
}
