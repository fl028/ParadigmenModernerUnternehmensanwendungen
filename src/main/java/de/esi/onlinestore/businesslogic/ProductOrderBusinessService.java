package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.Customer;
import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.DuplicateEmailException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.exceptions.TotalPriceTooLowException;
import de.esi.onlinestore.service.ProductOrderService;

import java.util.List;
import java.util.Optional;

public class ProductOrderBusinessService {

    private final String ENTITY_NAME = "ProductOrder";

    private final ProductOrderService productOrderService;

    private final OrderItemBusinessService orderItemBusinessService;

    private final CustomerBusinessService customerBusinessService;

    public ProductOrderBusinessService(ProductOrderService productOrderService,OrderItemBusinessService orderItemBusinessService,CustomerBusinessService customerBusinessService) {
        this.productOrderService = productOrderService;
        this.orderItemBusinessService = orderItemBusinessService;
        this.customerBusinessService = customerBusinessService;
    }

    public List<ProductOrder> getAll() {
        return productOrderService.findAll();
    }

    public ProductOrder getOne(Long id) throws ResourceNotFoundException {
        Optional<ProductOrder> searchProductOrder = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            return searchProductOrder.get();
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    public ProductOrder create(ProductOrder productorder) throws BadRequestException, TotalPriceTooLowException, ResourceNotFoundException, DuplicateEmailException {

        if (productorder.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        if(productorder.getOrderItems().size() < 1){
            String message = "A new " + ENTITY_NAME + " requires a order item";
            throw new BadRequestException(message);
        }

        checkBasketValue(productorder);
        updateCustomer(productorder);
        ProductOrder productOrder = productOrderService.save(productorder);
        updateOrderItems(productorder);

        return productOrder;
    }

    private void updateCustomer(ProductOrder productorder) throws DuplicateEmailException, BadRequestException, ResourceNotFoundException {
        Optional<Customer> searchCustomer = Optional.ofNullable(productorder.getCustomer());
        if(searchCustomer.isPresent()) {
            customerBusinessService.update(searchCustomer.get().getId(), searchCustomer.get() );
        }

    }

    private void checkBasketValue(ProductOrder productorder) throws TotalPriceTooLowException {
        //calculate total price of product order
        float productOrderTotalPrice = 0;
        for (OrderItem orderItem : productorder.getOrderItems()) {
            productOrderTotalPrice += orderItem.getTotalPrice();
        }

        //check basket value
        if(productOrderTotalPrice < 100){
            String message = "The " + ENTITY_NAME + " requires a order value of 100 but current value is "+ Float.toString(productOrderTotalPrice);
            throw new TotalPriceTooLowException(message);
        }
    }

    public ProductOrder update(Long id, ProductOrder productorder) throws BadRequestException, ResourceNotFoundException, DuplicateEmailException {
        if (id == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<ProductOrder> searchProductOrder = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            productorder.setId(id);
            updateCustomer(productorder);
            ProductOrder productOrder = productOrderService.save(productorder);
            updateOrderItems(productorder);
            return productOrder;
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

    private void updateOrderItems(ProductOrder productorder) throws BadRequestException, ResourceNotFoundException {
        if(productorder.getOrderItems() != null) {
            for (OrderItem orderItem : productorder.getOrderItems()) {
                orderItem.setOrder(productorder);
                orderItemBusinessService.update(orderItem.getId(),orderItem);
            }
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<ProductOrder> searchProductOrder  = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            productOrderService.delete(id);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + id);
        }
    }

}
