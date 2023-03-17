package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.DuplicateEmailException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final String ENTITY_NAME = "Customer";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> result= customerService.findAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws BadRequestException, DuplicateEmailException {
        if (customer.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        // check if email is unique
        if (isEmailUnique(customer.getEmail()) == false){
            String message = ENTITY_NAME + " email is not unique: " + customer.getEmail();
            throw new DuplicateEmailException(message);
        }

        Customer result = customerService.save(customer);
        return ResponseEntity.ok(result);
    }

    public boolean isEmailUnique(String email) {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long id,@Valid @RequestBody Customer customer) throws BadRequestException,ResourceNotFoundException,InternalError {
        Optional<Customer> searchCustomer = customerService.findOne(id);
        if(searchCustomer.isPresent()) {
            customer.setId(id);
            Customer result = customerService.save(customer);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + customer.getId());
        }
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (customer.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<Customer> searchCustomer = customerService.findOne(customer.getId());
        if(searchCustomer.isPresent()) {
            try {
                Customer result = customerService.save(customer);
                return ResponseEntity.ok(result);
            } catch (Exception e) {
                throw new InternalError();
            }
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + customer.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()) {
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }
}
