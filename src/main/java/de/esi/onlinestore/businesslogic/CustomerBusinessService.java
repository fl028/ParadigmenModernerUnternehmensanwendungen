package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.DuplicateEmailException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerBusinessService {

    private final String ENTITY_NAME = "Customer";

    private final CustomerService customerService;

    public CustomerBusinessService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> getAll() {
        return customerService.findAll();
    }

    public Customer getOne(Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()) {
            return customer.get();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    public Customer create(Customer customer) throws BadRequestException, DuplicateEmailException {
        if (customer.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        if (isEmailUnique(customer.getEmail()) == false){
            String message = ENTITY_NAME + " email is not unique: " + customer.getEmail();
            throw new DuplicateEmailException(message);
        }

        return customerService.save(customer);
    }

    private boolean isEmailUnique(String email) {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public Customer update(Long id, Customer customer) throws BadRequestException, ResourceNotFoundException {
        if (customer.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<Customer> searchCustomer = customerService.findOne(id);
        if(searchCustomer.isPresent()) {
            customer.setId(id);
            return customerService.save(customer);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + customer.getId());
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerService.findOne(id);
        if(customer.isPresent()) {
            customerService.delete(id);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }
}
