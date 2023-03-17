package de.esi.onlinestore.controller;

import de.esi.onlinestore.businesslogic.CustomerBusinessService;
import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.DuplicateEmailException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerBusinessService customerBusinessService;

    public CustomerController(CustomerService customerService) {
        this.customerBusinessService = new CustomerBusinessService(customerService);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerBusinessService.getAll());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws BadRequestException, DuplicateEmailException {
        return ResponseEntity.ok(customerBusinessService.create(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerBusinessService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long id,@Valid @RequestBody Customer customer) throws BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(customerBusinessService.update(id,customer));
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws  BadRequestException,ResourceNotFoundException,InternalError {
        return ResponseEntity.ok(customerBusinessService.update(customer.getId(),customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id)  throws ResourceNotFoundException {
        customerBusinessService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
