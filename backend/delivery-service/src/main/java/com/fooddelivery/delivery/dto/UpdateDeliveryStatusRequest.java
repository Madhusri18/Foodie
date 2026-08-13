package com.fooddelivery.delivery.dto;

import com.fooddelivery.delivery.entity.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for updating delivery status")
public class UpdateDeliveryStatusRequest {

    @Schema(
            description = "New status of the delivery",
            example = "ASSIGNED",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private DeliveryStatus status;

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}