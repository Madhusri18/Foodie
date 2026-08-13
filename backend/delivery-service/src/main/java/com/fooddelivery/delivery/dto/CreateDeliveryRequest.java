package com.fooddelivery.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Request body for creating a new delivery")
public class CreateDeliveryRequest {

    @Schema(
            description = "ID of the order for which the delivery is created",
            example = "101",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Long orderId;

    @Schema(
            description = "Address of the restaurant where the order will be picked up",
            example = "ABC Restaurant, Chennai",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String pickupAddress;

    @Schema(
            description = "Address where the order needs to be delivered",
            example = "123 Main Street, Chennai",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String deliveryAddress;

    @Schema(
            description = "Expected delivery time",
            example = "2026-08-13T22:00:00"
    )
    private LocalDateTime estimatedDeliveryTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}