package de.esi.onlinestore.repository;

import de.esi.onlinestore.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}