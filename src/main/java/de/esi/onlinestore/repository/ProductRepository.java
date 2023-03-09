package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product, Long> {
}