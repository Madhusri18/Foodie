package com.fooddelivery.restaurant.controller;

import com.fooddelivery.restaurant.dto.RestaurantRequest;
import com.fooddelivery.restaurant.entity.Restaurant;
import com.fooddelivery.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(
        name = "Restaurant",
        description = "Restaurant management APIs"
)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public ResponseEntity<List<Restaurant>>
    getAllRestaurants() {

        return ResponseEntity.ok(
                restaurantService.getAllRestaurants()
        );
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Get restaurant by ID")
    public ResponseEntity<Restaurant>
    getRestaurantById(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                restaurantService
                        .getRestaurantById(restaurantId)
        );
    }

    @PostMapping
    @Operation(summary = "Create restaurant")
    public ResponseEntity<Restaurant>
    createRestaurant(
            @Valid @RequestBody
            RestaurantRequest request) {

        Restaurant restaurant =
                restaurantService
                        .createRestaurant(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurant);
    }

    @PutMapping("/{restaurantId}")
    @Operation(summary = "Update restaurant")
    public ResponseEntity<Restaurant>
    updateRestaurant(
            @PathVariable Long restaurantId,
            @Valid @RequestBody
            RestaurantRequest request) {

        return ResponseEntity.ok(
                restaurantService.updateRestaurant(
                        restaurantId,
                        request
                )
        );
    }
}