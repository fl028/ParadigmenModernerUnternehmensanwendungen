package de.esi.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.esi.onlinestore.domain.enumeration.OrderItemStatus;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Die Klasse beschreibt Bestellpositionen.
 */


@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Min(value = 0)
    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "total_price")
    private Float totalPrice =0f;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderItemStatus status;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JsonIgnore
    private ProductOrder order;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Liefert die Produktmenge zurück.     *
     * @return Produktmenge in der Bestellposition als Integer.
     *
     */
    public Integer getQuantity() {
        return quantity;
    }


    /**
     * Legt die Produktmenge für die Bestellposition fest.
     * @param quantity Produktmenge
     *
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Liefert den Gesamtpreis der Bestellposition zurück.
     * @return Gesamtpreis der Bestellposition.
     *
     */
    public float getTotalPrice() {
        if(this.getProduct()!=null) {
            calculateTotalPrice();
        }
        return totalPrice;
    }
    private float calculateTotalPrice(){
        totalPrice = 0f;
        if(this.getProduct()!=null) {
            totalPrice = this.product.getPrice() * this.quantity;
        }
        return totalPrice;
    }


    /**
     * Legt den Gesamtpreis der Bestellposition fest.
     * @param totalPrice Gesamtpreis der Bestellposition
     *
     */
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Liefert den Status der Bestellposition zurück.
     * @return Status (AVAILABLE, OUT_OF_STOCK) der Bestellposition.
     *
     */

    public OrderItemStatus getStatus() {
        return status;
    }


    /**
     * Setzt den Status der Bestellposition.
     * @param status Status (AVAILABLE, OUT_OF_STOCK) der Bestellposition
     *
     */

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    /**
     * Liefert das Produkt in der Bestellposition.
     * @return das Bestellte Produkt in der Bestellposition.
     *
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Legt das Produkt in der Bestellposition fest.
     * @param product das Produkt in der Bestellposition
     *
     */

    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Liefert die Bestellung zurück, zu der die Bestellposition gehört.
     * @return Bestellung
     *
     */
    public ProductOrder getOrder() {
        return order;
    }

    /**
     * Legt die Bestellung fest, zu der die Bestellposition gehört.
     * @param productOrder Bestellung, zu der die Bestellposition gehört
     *
     */
    public void setOrder(ProductOrder productOrder) {
        this.order = productOrder;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItem)) {
            return false;
        }
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", totalPrice=" + getTotalPrice() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
