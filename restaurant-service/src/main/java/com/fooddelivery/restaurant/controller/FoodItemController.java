package com.fooddelivery.restaurant.controller;

import com.fooddelivery.restaurant.dto.FoodRequest;
import com.fooddelivery.restaurant.entity.FoodItem;
import com.fooddelivery.restaurant.service.FoodItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Food",
        description = "Food item management APIs"
)
public class FoodItemController {

    private final FoodItemService foodItemService;

    @GetMapping(
            "/api/restaurants/{restaurantId}/foods"
    )
    @Operation(summary = "Get foods for a restaurant")
    public ResponseEntity<List<FoodItem>>
    getFoodsByRestaurant(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                foodItemService
                        .getFoodsByRestaurant(restaurantId)
        );
    }

    @GetMapping("/api/foods/{foodId}")
    @Operation(summary = "Get food by ID")
    public ResponseEntity<FoodItem>
    getFoodById(
            @PathVariable Long foodId) {

        return ResponseEntity.ok(
                foodItemService.getFoodById(foodId)
        );
    }

    @PostMapping(
            "/api/restaurants/{restaurantId}/foods"
    )
    @Operation(summary = "Add food to restaurant")
    public ResponseEntity<FoodItem>
    createFood(
            @PathVariable Long restaurantId,
            @Valid @RequestBody
            FoodRequest request) {

        FoodItem foodItem =
                foodItemService.createFood(
                        restaurantId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(foodItem);
    }

    @PutMapping("/api/foods/{foodId}")
    @Operation(summary = "Update food")
    public ResponseEntity<FoodItem>
    updateFood(
            @PathVariable Long foodId,
            @Valid @RequestBody
            FoodRequest request) {

        return ResponseEntity.ok(
                foodItemService.updateFood(
                        foodId,
                        request
                )
        );
    }

    @DeleteMapping("/api/foods/{foodId}")
    @Operation(summary = "Delete food")
    public ResponseEntity<Void>
    deleteFood(
            @PathVariable Long foodId) {

        foodItemService.deleteFood(foodId);

        return ResponseEntity.noContent().build();
    }
}