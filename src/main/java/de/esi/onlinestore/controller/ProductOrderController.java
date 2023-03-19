package de.esi.onlinestore.controller;

import de.esi.onlinestore.businesslogic.CustomerBusinessService;
import de.esi.onlinestore.businesslogic.OrderItemBusinessService;
import de.esi.onlinestore.businesslogic.ProductOrderBusinessService;
import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.DuplicateEmailException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.exceptions.TotalPriceTooLowException;
import de.esi.onlinestore.service.CustomerService;
import de.esi.onlinestore.service.OrderItemService;
import de.esi.onlinestore.service.ProductOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/productorders")
public class ProductOrderController {

    private final ProductOrderBusinessService productOrderBusinessService;

    public ProductOrderController(ProductOrderService productOrderService, OrderItemService orderItemService, CustomerService customerService) {
        this.productOrderBusinessService = new ProductOrderBusinessService(productOrderService,new OrderItemBusinessService(orderItemService),new CustomerBusinessService(customerService));
    }


    @GetMapping
    public ResponseEntity<List<ProductOrder>> getAllproductorders() {
        return ResponseEntity.ok(productOrderBusinessService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductOrder> createProductOrder(@Valid @RequestBody ProductOrder productorder) throws BadRequestException, TotalPriceTooLowException, ResourceNotFoundException, DuplicateEmailException {
        return ResponseEntity.ok(productOrderBusinessService.create(productorder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrder> getProductOrder(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productOrderBusinessService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable(value = "id") Long id,@Valid @RequestBody  ProductOrder productorder) throws BadRequestException, ResourceNotFoundException, InternalError, DuplicateEmailException {
        return ResponseEntity.ok(productOrderBusinessService.update(id,productorder));
    }

    @PutMapping
    public ResponseEntity<ProductOrder> updateProductOrderById(@Valid @RequestBody  ProductOrder productorder) throws BadRequestException, ResourceNotFoundException, InternalError, DuplicateEmailException {
        return ResponseEntity.ok(productOrderBusinessService.update(productorder.getId(),productorder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductOrder(@PathVariable Long id)  throws ResourceNotFoundException {
        productOrderBusinessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
