package de.esi.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import java.io.Serializable;
import de.esi.onlinestore.domain.enumeration.*;

/**
 * Die Klasse beschreibt ein Produkte.
 */

@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private float price;


    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size = Size.M;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnore
    private ProductCategory productCategory;

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

    public float getPrice() {
        return price;
    }



    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Liefert die Größe des Produkts.
     *
     * @return Produktgröße (S,M,L,XL,XXL).
     *
     */
    public Size getSize() {
        return size;
    }


    /**
     * Legt Produktgröße fest.
     *
     * @param size neue Produktgröße.
     *
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Liefert Produktbild zurück.
     *
     * @return Produktbild als Byte-Array.
     *
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Legt Produktbild fest.
     *
     * @param image neues Bild des Produkts.
     *
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Liefert Produktbild zurück.
     *
     * @return Produktbild als Byte-Array.
     *
     */
    public String getImageContentType() {
        return imageContentType;
    }


    /**
     * Legt Typ des Produktbildes fest.
     *
     * @param imageContentType Bildtyp des Produktbildes.
     *
     */
    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }


    /**
     * Liefert Kategorie des Produkts zurück.
     *
     * @return Produktkategorie.
     *
     */
    public ProductCategory getProductCategory() {
        return productCategory;
    }


    /**
     * Legt Produktkategorie fest.
     *
     * @param productCategory Produktkategorie.
     *
     */
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return getName() + " Preis=" +getPrice() +" size=" + getSize() ;
    }
}
