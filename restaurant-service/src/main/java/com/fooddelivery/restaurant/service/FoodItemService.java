package com.fooddelivery.restaurant.service;

import com.fooddelivery.restaurant.dto.FoodRequest;
import com.fooddelivery.restaurant.entity.FoodItem;
import com.fooddelivery.restaurant.exception.FoodNotFoundException;
import com.fooddelivery.restaurant.exception.RestaurantNotFoundException;
import com.fooddelivery.restaurant.repository.FoodItemRepository;
import com.fooddelivery.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;

    public List<FoodItem> getFoodsByRestaurant(
            Long restaurantId) {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId);
        }

        return foodItemRepository
                .findByRestaurantId(restaurantId);
    }

    public FoodItem getFoodById(Long foodId) {

        return foodItemRepository.findById(foodId)
                .orElseThrow(() ->
                        new FoodNotFoundException(foodId));
    }

    public FoodItem createFood(
            Long restaurantId,
            FoodRequest request) {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId);
        }

        FoodItem foodItem = FoodItem.builder()
                .restaurantId(restaurantId)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .available(request.getAvailable())
                .build();

        return foodItemRepository.save(foodItem);
    }

    public FoodItem updateFood(
            Long foodId,
            FoodRequest request) {

        FoodItem foodItem =
                getFoodById(foodId);

        if (!restaurantRepository.existsById(
                request.getRestaurantId())) {

            throw new RestaurantNotFoundException(
                    request.getRestaurantId());
        }

        foodItem.setRestaurantId(
                request.getRestaurantId());

        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setCategory(request.getCategory());
        foodItem.setAvailable(request.getAvailable());

        return foodItemRepository.save(foodItem);
    }

    public void deleteFood(Long foodId) {

        FoodItem foodItem =
                getFoodById(foodId);

        foodItemRepository.delete(foodItem);
    }
}