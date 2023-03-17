package de.esi.onlinestore.businesslogic;

import de.esi.onlinestore.domain.OrderItem;
import de.esi.onlinestore.domain.ProductOrder;
import de.esi.onlinestore.exceptions.BadRequestException;
import de.esi.onlinestore.exceptions.ResourceNotFoundException;
import de.esi.onlinestore.exceptions.TotalPriceTooLowException;
import de.esi.onlinestore.service.ProductOrderService;

import java.util.List;
import java.util.Optional;

public class ProductOrderBusinessService {

    private final String ENTITY_NAME = "ProductOrder";

    private final ProductOrderService productOrderService;

    public ProductOrderBusinessService(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
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

    public ProductOrder create(ProductOrder productorder) throws BadRequestException, TotalPriceTooLowException {

        if (productorder.getId() != null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        if(productorder.getOrderItems().size() < 1){
            String message = "A new " + ENTITY_NAME + " requires a order item";
            throw new BadRequestException(message);
        }

        //calculate total price of product order
        float productOrderTotalPrice = 0;
        for (OrderItem orderItem : productorder.getOrderItems()) {
            productOrderTotalPrice += orderItem.getTotalPrice();
        }

        //check basket value
        if(productOrderTotalPrice < 100){
            String message = "The " + ENTITY_NAME + "  requires a order value of 100 but current value is "+ Float.toString(productOrderTotalPrice) +": " + ENTITY_NAME;
            throw new TotalPriceTooLowException(message);
        }

        return productOrderService.save(productorder);
    }

    public ProductOrder update(Long id, ProductOrder productorder) throws BadRequestException, ResourceNotFoundException {
        if (productorder.getId() == null) {
            String message = "Invalid  " + ENTITY_NAME + " id";
            throw new BadRequestException(message);
        }

        Optional<ProductOrder> searchProductOrder = productOrderService.findOne(id);
        if(searchProductOrder.isPresent()) {
            productorder.setId(id);
            return productOrderService.save(productorder);
        }
        else{
            throw new ResourceNotFoundException("No " + ENTITY_NAME + " with id: " + productorder.getId());
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
