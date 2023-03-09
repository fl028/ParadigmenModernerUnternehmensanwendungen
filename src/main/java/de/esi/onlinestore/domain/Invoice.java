package de.esi.onlinestore.domain;

import de.esi.onlinestore.domain.enumeration.InvoiceStatus;
import de.esi.onlinestore.domain.enumeration.PaymentMethod;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private float paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status = InvoiceStatus.ISSUED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod = PaymentMethod.CASH_ON_DELIVERY;

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ProductOrder getOrder() {
        return order;
    }

    private String details;

    @OneToOne
    private ProductOrder order;


    public void setOrder(ProductOrder productOrder) {
        this.order = productOrder;
    }
}
