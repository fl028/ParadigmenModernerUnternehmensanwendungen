package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}