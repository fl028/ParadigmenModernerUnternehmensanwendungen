package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.exceptions.TotalPriceTooLowException;
import de.esi.onlinestore.service.ProductOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productorders")
public class ProductOrderController {

    private final String ENTITY_NAME = "ProductOrder";

    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @GetMapping
    public ResponseEntity<List<ProductOrder>> getAllproductorders() {
        List<ProductOrder> result= productOrderService.findAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProductOrder> createProductOrder(@RequestBody ProductOrder productorder) throws BadRequestException,TotalPriceTooLowException {
        if (productorder.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        if(productorder.getOrderItems().size() < 1){
            String message = "A new " + ENTITY_NAME + " requires a order item";
            throw new BadRequestException(message);
        }

        //calculate total price of product order
        float productOrderTotalPrice = 0;
        for (OrderItem orderItem : productorder.getOrderItems()) {
            productOrderTotalPrice += orderItem.getTotalPrice();
        }

        //check basket value
        if(productOrderTotalPrice < 100){
            String message = "The " + ENTITY_NAME + "  requires a order value of 100 but current value is "+ Float.toString(productOrderTotalPrice) +": " + ENTITY_NAME;
            throw new TotalPriceTooLowException(message);
        }

        ProductOrder result = productOrderService.save(productorder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrder> getProductOrder(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<ProductOrder> searchProductOrder = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            return ResponseEntity.ok(searchProductOrder.get());
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable(value = "id") Long id,@Valid @RequestBody  ProductOrder productorder) throws BadRequestException,ResourceNotFoundException,InternalError {
        Optional<ProductOrder> searchProductOrder = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            ProductOrder result = productOrderService.save(productorder);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + productorder.getId());
        }
    }

    @PutMapping
    public ResponseEntity<ProductOrder> updateProductOrderById(@RequestBody  ProductOrder productorder) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (productorder.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<ProductOrder> searchProductOrder = productOrderService.findOne(productorder.getId());
        if(searchProductOrder.isPresent()) {
            ProductOrder result = productOrderService.save(productorder);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + productorder.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<ProductOrder> searchProductOrder  = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            productOrderService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }
}
