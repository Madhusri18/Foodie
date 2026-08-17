package com.fooddelivery.order.service;

import com.fooddelivery.order.entity.Order;
import com.fooddelivery.order.entity.OrderStatus;
import com.fooddelivery.order.exception.OrderNotFoundException;
import com.fooddelivery.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {

        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(orderId));
    }

    public List<Order> getOrdersByUserId(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(
            Long orderId,
            OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(orderId));

        order.setStatus(status);

        return orderRepository.save(order);
    }
}