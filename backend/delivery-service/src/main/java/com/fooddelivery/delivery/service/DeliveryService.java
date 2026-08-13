package com.fooddelivery.delivery.service;

import com.fooddelivery.delivery.entity.Delivery;
import com.fooddelivery.delivery.entity.DeliveryStatus;
import com.fooddelivery.delivery.exception.DeliveryAlreadyExistsException;
import com.fooddelivery.delivery.exception.DeliveryNotFoundException;
import com.fooddelivery.delivery.exception.InvalidDeliveryStatusException;
import com.fooddelivery.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    // Create a new delivery
    public Delivery createDelivery(Delivery delivery) {

        // Check if a delivery already exists for this order
        if (deliveryRepository.findByOrderId(delivery.getOrderId()).isPresent()) {
            throw new DeliveryAlreadyExistsException(
                    "Delivery already exists for order id: " + delivery.getOrderId()
            );
        }

        // New deliveries always start with PENDING status
        delivery.setStatus(DeliveryStatus.PENDING);

        return deliveryRepository.save(delivery);
    }

    // Get delivery by delivery ID
    public Delivery getDeliveryById(Long deliveryId) {

        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException(
                        "Delivery not found with id: " + deliveryId
                ));
    }

    // Get delivery by order ID
    public Delivery getDeliveryByOrderId(Long orderId) {

        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DeliveryNotFoundException(
                        "Delivery not found for order id: " + orderId
                ));
    }

    // Update delivery status
    public Delivery updateDeliveryStatus(
            Long deliveryId,
            DeliveryStatus newStatus
    ) {

        Delivery delivery = getDeliveryById(deliveryId);

        DeliveryStatus currentStatus = delivery.getStatus();

        // Check whether the status transition is valid
        boolean validTransition =
                (currentStatus == DeliveryStatus.PENDING
                        && newStatus == DeliveryStatus.ASSIGNED)

                || (currentStatus == DeliveryStatus.ASSIGNED
                        && newStatus == DeliveryStatus.PICKED_UP)

                || (currentStatus == DeliveryStatus.PICKED_UP
                        && newStatus == DeliveryStatus.OUT_FOR_DELIVERY)

                || (currentStatus == DeliveryStatus.OUT_FOR_DELIVERY
                        && newStatus == DeliveryStatus.DELIVERED);

        if (!validTransition) {
            throw new InvalidDeliveryStatusException(
                    "Invalid status transition from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }

        delivery.setStatus(newStatus);

        // Record assignment time
        if (newStatus == DeliveryStatus.ASSIGNED) {
            delivery.setAssignedAt(LocalDateTime.now());
        }

        // Record delivery completion time
        if (newStatus == DeliveryStatus.DELIVERED) {
            delivery.setDeliveredAt(LocalDateTime.now());
        }

        return deliveryRepository.save(delivery);
    }

    // Get all deliveries
    public List<Delivery> getAllDeliveries() {

        return deliveryRepository.findAll();
    }
}