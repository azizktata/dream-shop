package com.example.dreamshops.controller;

import com.example.dreamshops.dto.CartDto;
import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.request.CartItemRequest;
import com.example.dreamshops.service.Cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }


    @GetMapping("/{id}/cart-item")
    public List<CartItemDto> getCartItems(@PathVariable Long id) {
        return cartService.getCartItems(id);
    }


    @PostMapping("/cart-item/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto addCartItem(@RequestBody CartItemRequest request, @PathVariable Long userId) {
        return cartService.addCartItem(request, userId);
    }

    @PutMapping("/cart-item/user/{userId}")
    public void updateCartItem(@RequestBody CartItemRequest request, @PathVariable Long userId) {
        cartService.updateCartItem(request, userId);
    }

    @DeleteMapping("/{id}/cart-item/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable Long id, @PathVariable Long cartItemId) {
        cartService.removeCartItem(id, cartItemId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(Long id) {
        cartService.clearCart(id);
    }
}
