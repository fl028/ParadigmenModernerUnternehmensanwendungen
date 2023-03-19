package de.esi.onlinestore.controller;

import de.esi.onlinestore.businesslogic.OrderItemBusinessService;
import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {

    private final OrderItemBusinessService orderItemBusinessService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemBusinessService = new OrderItemBusinessService(orderItemService);
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllorderitems() {
        return ResponseEntity.ok(orderItemBusinessService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItem orderitem) throws BadRequestException {
        return ResponseEntity.ok(orderItemBusinessService.create(orderitem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderItemBusinessService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@Valid @PathVariable(value = "id") Long id,@Valid @RequestBody  OrderItem orderitem) throws BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(orderItemBusinessService.update(id,orderitem));
    }

    @PutMapping
    public ResponseEntity<OrderItem> updateOrderItemById(@Valid @RequestBody OrderItem orderitem) throws  BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(orderItemBusinessService.update(orderitem.getId(),orderitem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id)  throws ResourceNotFoundException {
        orderItemBusinessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
