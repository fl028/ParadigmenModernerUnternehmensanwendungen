package de.esi.onlinestore.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse beschreibt Kunden.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;




    @Column(name = "email")
    private String email;


   @JsonIgnoreProperties("customer")
   @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
   private Set<ProductOrder> orders = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Liefert den Vornamen des Kunden.
     *
     * @return Vorname des Kunden als String.
     *
     */
    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ProductOrder> getOrders() {
        return orders;
    }


    /**
     * Fügt weitere Bestellung zu den Bestellungen des Kunden hinzu.
     * @param productOrder Bestellung, die hinzugefügt werden soll
     * @return aktualisierte Instanz der Klasse Kunde {@link Customer}.
     *
     */
    public Customer addOrder(ProductOrder productOrder) {
        this.orders.add(productOrder);
        productOrder.setCustomer(this);
        return this;
    }

    /**
     * Entfernt angegebene Bestellung aus der Liste der Bestellungen des Kunden.
     * @param productOrder Bestellung, die entfernt werden soll
     * @return aktualisierte Instanz der Klasse Kunde {@link Customer}.
     *
     */
    public Customer removeOrder(ProductOrder productOrder) {
        this.orders.remove(productOrder);
        productOrder.setCustomer(null);
        return this;
    }

    /**
     * Setzt Liste der Bestellungen des Kunden.
     * @param productOrders Neue Liste von Bestellungen     *
     *
     */

    public void setOrders(Set<ProductOrder> productOrders) {
        this.orders = productOrders;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return getFirstName() + " " +getLastName();
    }
}
