package com.fooddelivery.order.exception;

public class OrderItemNotFoundException extends RuntimeException {

    public OrderItemNotFoundException(Long itemId) {
        super("Order item not found with id: " + itemId);
    }
}