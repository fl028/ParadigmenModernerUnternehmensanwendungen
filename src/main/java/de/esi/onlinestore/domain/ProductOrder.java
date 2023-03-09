package de.esi.onlinestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.esi.onlinestore.domain.enumeration.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse beschreibt Bestellungen
 */
@Entity
@Table(name = "product_order")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class  ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "placed_date")
    private Instant placedDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.PENDING;


    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();



    @ManyToOne
    //@JsonIgnore
    @JsonIgnoreProperties("orders")
    private Customer customer;

    @OneToOne(mappedBy="order")
    private Invoice invoice;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Liefert den Erstellungszeitpunkt (als Zeitstempel, Datentyp: Instant) der Bestellung zurück.
     *
     * @return Erstellungszeitpunkt der Bestellung.
     *
     */
    public Instant getPlacedDate() {
        return placedDate;
    }

    /**
     * Legt den Erstellungszeitpunkt der Bestellung fest.
     *
     * @param placedDate Erstellungszeitpunkt der Bestellung.
     *
     */
    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    /**
     * Legt den Status (COMPLETED, PENDING, CANCELLED) der Bestellung zurück.
     *
     * @return Status der Bestellung.
     *
     */
    public OrderStatus getStatus() {
        return status;
    }


    /**
     * Legt den Status der Bestellung fest.
     *
     * @param status neuer Status der Bestellung.
     *
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }



    /**
     * Liefert die Bestellpositionen der Bestellung zurück.
     *
     * @return Liste von Bestellpositionen.
     *
     */
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }


    /**
     * Fügt eine Bestellposition zur Bestellung hinzu.
     *
     * @param orderItem Bestellposition, die zur Bestellung hinzugefügt werden soll.
     *
     */
    public ProductOrder addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        return this;
    }

    /**
     * Entfernt Bestellposition aus der aktuellen Bestellung.
     *
     * @param orderItem Bestellposition, die aus der Bestellung entfernt werden soll.
     *
     */
    public ProductOrder removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
        return this;
    }

    /**
     * Legt Bestellpositionen der Bestellung fest.
     *
     * @param orderItems Bestellpositionen, die der Bestellung zugeordnet werden sollen.
     *
     */
    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Liefert den Kunden, der die Bestellung aufgegeben hat, zurück.
     *
     * @return Kunde.
     *
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Legt Kunde dieser Bestellung fest.
     *
     * @param customer Kunde, der die Bestellung aufgegeben hat.
     *
     */

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOrder)) {
            return false;
        }
        return id != null && id.equals(((ProductOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + getId() +
                ", placedDate='" + getPlacedDate() + "'" +
                ", status='" + getStatus() + "'" +
                "}";
    }
}


