package com.fooddelivery.order.controller;

import com.fooddelivery.order.entity.OrderItem;
import com.fooddelivery.order.service.OrderItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderItemService.getItemsByOrderId(orderId)
        );
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItem> addOrderItem(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderItemRequest request) {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderId(orderId);
        orderItem.setFoodItemId(request.getFoodItemId());
        orderItem.setFoodName(request.getFoodName());
        orderItem.setPrice(request.getPrice());
        orderItem.setQuantity(request.getQuantity());

        return ResponseEntity.ok(
                orderItemService.addOrderItem(orderItem)
        );
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<OrderItem> updateOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateOrderItemRequest request) {

        return ResponseEntity.ok(
                orderItemService.updateOrderItem(
                        itemId,
                        request.getQuantity()
                )
        );
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> deleteOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {

        orderItemService.deleteOrderItem(itemId);

        return ResponseEntity.noContent().build();
    }

    public static class OrderItemRequest {

        @NotNull
        private Long foodItemId;

        private String foodName;

        @NotNull
        private java.math.BigDecimal price;

        @NotNull
        @Min(1)
        private Integer quantity;

        public Long getFoodItemId() {
            return foodItemId;
        }

        public void setFoodItemId(Long foodItemId) {
            this.foodItemId = foodItemId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public java.math.BigDecimal getPrice() {
            return price;
        }

        public void setPrice(java.math.BigDecimal price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public static class UpdateOrderItemRequest {

        @NotNull
        @Min(1)
        private Integer quantity;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}