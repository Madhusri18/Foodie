package com.fooddelivery.delivery.controller;

import com.fooddelivery.delivery.dto.CreateDeliveryRequest;
import com.fooddelivery.delivery.dto.UpdateDeliveryStatusRequest;
import com.fooddelivery.delivery.entity.Delivery;
import com.fooddelivery.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(
            summary = "Create a new delivery",
            description = "Creates a delivery for an order with PENDING status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Delivery created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            )
    })
    @PostMapping
    public ResponseEntity<Delivery> createDelivery(
            @Valid @RequestBody CreateDeliveryRequest request) {

        Delivery delivery = new Delivery();

        delivery.setOrderId(request.getOrderId());
        delivery.setPickupAddress(request.getPickupAddress());
        delivery.setDeliveryAddress(request.getDeliveryAddress());
        delivery.setEstimatedDeliveryTime(
                request.getEstimatedDeliveryTime()
        );

        Delivery savedDelivery = deliveryService.createDelivery(delivery);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDelivery);
    }

    @Operation(
            summary = "Get delivery by ID",
            description = "Returns delivery details using the delivery ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Delivery not found"
            )
    })
    @GetMapping("/{deliveryId}")
    public ResponseEntity<Delivery> getDelivery(
            @PathVariable Long deliveryId) {

        return ResponseEntity.ok(
                deliveryService.getDeliveryById(deliveryId)
        );
    }

    @Operation(
            summary = "Get delivery by order ID",
            description = "Returns the delivery associated with an order."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Delivery not found for the order"
            )
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Delivery> getDeliveryByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                deliveryService.getDeliveryByOrderId(orderId)
        );
    }

    @Operation(
            summary = "Update delivery status",
            description = "Updates the delivery status according to the allowed delivery workflow."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid status transition or request data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Delivery not found"
            )
    })
    @PutMapping("/{deliveryId}/status")
    public ResponseEntity<Delivery> updateDeliveryStatus(
            @PathVariable Long deliveryId,
            @Valid @RequestBody UpdateDeliveryStatusRequest request) {

        Delivery updatedDelivery =
                deliveryService.updateDeliveryStatus(
                        deliveryId,
                        request.getStatus()
                );

        return ResponseEntity.ok(updatedDelivery);
    }
}