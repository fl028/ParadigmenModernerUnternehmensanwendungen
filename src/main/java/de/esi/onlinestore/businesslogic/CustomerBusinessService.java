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

        checkEmail(customer);
        return customerService.save(customer);
    }

    private void checkEmail(Customer customer) throws DuplicateEmailException {
        if (isEmailUnique(customer.getId(),customer.getEmail()) == false){
            String message = ENTITY_NAME + " email is not unique: " + customer.getEmail();
            throw new DuplicateEmailException(message);
        }
    }

    private boolean isEmailUnique(Long id,String email) {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            // email is not unique when it appears in another customer
            if (customer.getEmail().equals(email) && checkIdIsNotEqual(id,customer.getId())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIdIsNotEqual(Long newCustomerId, Long existingCustomerId) {
        if(newCustomerId == null){
            return true;
        }
        if(newCustomerId.equals(existingCustomerId)){
            return false;
        }
        return true;
    }

    public Customer update(Long id, Customer customer) throws BadRequestException, ResourceNotFoundException, DuplicateEmailException {
        if (id == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<Customer> searchCustomer = customerService.findOne(id);
        if(searchCustomer.isPresent()) {
            customer.setId(id);
            checkEmail(customer);
            return customerService.save(customer);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
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
