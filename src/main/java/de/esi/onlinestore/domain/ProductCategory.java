package de.esi.onlinestore.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse beschreibt Produktkategorien
 */
@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "productCategory")
    private Set<Product> products = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Liefert die Produkte dieser Kategorie zurück.
     *
     * @return Produkte der aktuellen Kategorie.
     *
     */
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * Fügt Produkt zur aktuellen Kategorie hinzu.
     *
     * @param product das Produkt, das zur aktuellen Kategorie hinzugefügt werden soll.
     * @return die aktuelle Kategorie
     *
     */
    public ProductCategory addProduct(Product product) {
        this.products.add(product);
        product.setProductCategory(this);
        return this;
    }

    /**
     * Entfernt ein Produkt aus der Kategorie.
     *
     * @param product das Produkt, das aus der aktuellen Kategorie entfernt werden soll.
     *
     */
    public ProductCategory removeProduct(Product product) {
        this.products.remove(product);
        product.setProductCategory(null);
        return this;
    }

    /**
     * Legt Produkte der Kategorie fest.
     *
     * @param products Liste von Produkten, die zur Kategorie gehören.
     *
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
