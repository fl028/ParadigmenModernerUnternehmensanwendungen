package de.esi.onlinestore.controller;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    // 1. Liste aller Kunden aufrufen GET /customers
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> result= customerService.findAll();
        return ResponseEntity.ok(result);
    }

    // 2. Neuen Kunden erstellen POST /customers
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws BadRequestException {
        if (customer.getId() != null) {

            String message = "A new customer cannot already have an ID: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        // check if email is unique
        if (isEmailUnique(customer.getEmail()) == false){
            String message = "Email is not unique: " + ENTITY_NAME;
            throw new BadRequestException(message);
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

    // 3. Kunde aufrufen (Parameter: Id) GET /customers/{id}

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        else{
            throw new ResourceNotFoundException("No customer with id: " + id);
        }
    }

    // 4. Kunde überschreiben (Parameter: Id) PUT /customers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long id,@Valid @RequestBody Customer customer) throws BadRequestException,ResourceNotFoundException,InternalError {
        Optional<Customer> searchCustomer = customerService.findOne(id);
        if(searchCustomer.isPresent()) {
            Customer result = customerService.save(customer);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No customer with id: " + customer.getId());
        }
    }

    // 5. Kunde überschreiben (Parameter: Instanz von Kunde mit aktuellen Werten) PUT /customers
    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws  BadRequestException,ResourceNotFoundException,InternalError {
        if (customer.getId() == null) {
            String message = "Invalid id: " + ENTITY_NAME;
            throw new BadRequestException(message);
        }

        Optional<Customer> searchCustomer = customerService.findOne(customer.getId());
        if(searchCustomer.isPresent()) {
            Customer result = customerService.save(customer);
            return ResponseEntity.ok(result);
        }
        else{
            throw new ResourceNotFoundException("No customer with id: " + customer.getId());
        }
    }

    // 6. Kunde löschen (Parameter: Id) DELETE /customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()) {
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        }
        else{
            throw new ResourceNotFoundException("No customer with id: " + id);
        }
    }
}
