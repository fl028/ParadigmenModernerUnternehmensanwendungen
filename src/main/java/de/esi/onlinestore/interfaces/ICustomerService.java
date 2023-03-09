package de.esi.onlinestore.interfaces;

import de.esi.onlinestore.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    public Customer save(Customer customer);

    public void delete(Long id);

    public List<Customer> findAll();

    public Optional<Customer> findOne(Long id);
}
