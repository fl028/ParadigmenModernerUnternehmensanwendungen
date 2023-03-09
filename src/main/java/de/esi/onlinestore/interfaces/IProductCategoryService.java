package de.esi.onlinestore.interfaces;

import de.esi.onlinestore.domain.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface IProductCategoryService {

    public ProductCategory save(ProductCategory productcategory);

    public void delete(Long id);

    public List<ProductCategory> findAll();

    public Optional<ProductCategory> findOne(Long id);
}
