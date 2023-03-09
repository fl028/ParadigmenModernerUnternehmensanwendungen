package de.esi.onlinestore.interfaces;

import de.esi.onlinestore.domain.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    public Product save(Product product);

    public void delete(Long id);

    public List<Product> findAll();

    public Optional<Product> findOne(Long id);
}
