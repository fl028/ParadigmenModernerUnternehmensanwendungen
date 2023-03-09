package de.esi.onlinestore.interfaces;

import de.esi.onlinestore.domain.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface IProductOrderService {

    public ProductOrder save(ProductOrder productorder);

    public void delete(Long id);

    public List<ProductOrder> findAll();

    public Optional<ProductOrder> findOne(Long id);
}
