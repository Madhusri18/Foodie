package com.fooddelivery.order.service;

import com.fooddelivery.order.entity.OrderItem;
import com.fooddelivery.order.exception.OrderItemNotFoundException;
import com.fooddelivery.order.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem addOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Long itemId, Integer quantity) {

        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new OrderItemNotFoundException(itemId));

        orderItem.setQuantity(quantity);

        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long itemId) {

        if (!orderItemRepository.existsById(itemId)) {
            throw new OrderItemNotFoundException(itemId);
        }

        orderItemRepository.deleteById(itemId);
    }
}