package com.example.dreamshops.controller;

import com.example.dreamshops.dto.CartDto;
import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.request.CartItemRequest;
import com.example.dreamshops.service.Cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }

    @GetMapping("/user/{id}")
    public CartDto getCartByUser(@PathVariable Long id) {
        return cartService.getCartByUser(id);
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

    @PutMapping("/cart-item/{cartItemId}/user/{userId}")
    public void updateCartItem(@RequestParam("quantity") Integer quantity, @PathVariable Long cartItemId ,@PathVariable Long userId) {
        cartService.updateCartItem(quantity, cartItemId, userId);
    }

    @DeleteMapping("/cart-item/{cartItemId}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable Long id) {
        cartService.clearCart(id);
    }
}
