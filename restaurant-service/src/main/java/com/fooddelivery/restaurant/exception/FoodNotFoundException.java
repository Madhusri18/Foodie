package com.fooddelivery.restaurant.exception;

public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException(Long id) {
        super("Food item not found with id: " + id);
    }
}