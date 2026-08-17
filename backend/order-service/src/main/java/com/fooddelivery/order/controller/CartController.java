package com.fooddelivery.order.controller;

import com.fooddelivery.order.dto.AddCartItemRequest;
import com.fooddelivery.order.dto.UpdateCartItemRequest;
import com.fooddelivery.order.entity.Cart;
import com.fooddelivery.order.entity.CartItem;
import com.fooddelivery.order.service.CartItemService;
import com.fooddelivery.order.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    public CartController(
            CartService cartService,
            CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {

        Cart cart = cartService.getCartByUserId(userId);

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addCartItem(
            @Valid @RequestBody AddCartItemRequest request) {

        CartItem cartItem = new CartItem();

        cartItem.setCartId(request.getCartId());
        cartItem.setFoodItemId(request.getFoodItemId());
        cartItem.setQuantity(request.getQuantity());

        return ResponseEntity.ok(
                cartItemService.addItem(cartItem)
        );
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        CartItem updatedItem =
                cartItemService.updateItem(
                        cartItemId,
                        request.getQuantity()
                );

        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable Long cartItemId) {

        cartItemService.deleteItem(cartItemId);

        return ResponseEntity.noContent().build();
    }
}