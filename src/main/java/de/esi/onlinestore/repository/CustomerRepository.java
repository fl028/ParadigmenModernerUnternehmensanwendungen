package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}