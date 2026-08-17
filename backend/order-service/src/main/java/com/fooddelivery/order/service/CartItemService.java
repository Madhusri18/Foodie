package com.fooddelivery.order.service;

import com.fooddelivery.order.entity.CartItem;
import com.fooddelivery.order.exception.CartItemNotFoundException;
import com.fooddelivery.order.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public CartItem addItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateItem(Long cartItemId, Integer quantity) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartItemNotFoundException(cartItemId));

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    public void deleteItem(Long cartItemId) {

        if (!cartItemRepository.existsById(cartItemId)) {
            throw new CartItemNotFoundException(cartItemId);
        }

        cartItemRepository.deleteById(cartItemId);
    }
}