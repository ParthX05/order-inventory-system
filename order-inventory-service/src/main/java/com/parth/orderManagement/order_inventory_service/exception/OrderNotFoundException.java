package com.parth.orderManagement.order_inventory_service.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException (String message){
        super(message);
    }
}
