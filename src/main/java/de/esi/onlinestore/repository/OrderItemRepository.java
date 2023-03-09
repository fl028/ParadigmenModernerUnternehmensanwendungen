package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}