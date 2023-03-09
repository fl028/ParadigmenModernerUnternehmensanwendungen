package de.esi.onlinestore.service;


import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.repository.CustomerRepository;
import de.esi.onlinestore.interfaces.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        logger.debug("Request to save Customer {}", customer);
        Customer result = null;
        try{
            result = customerRepository.save(customer);
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error(ex.toString());
        }
        return result;
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Customer {}", id);
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAll() {
        logger.debug("Request to get all Customer");
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findOne(Long id) {
        logger.debug("Request to find Customer {}", id);
        return customerRepository.findById(id);
    }
}
