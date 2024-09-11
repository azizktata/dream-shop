package com.example.dreamshops.controller;

import com.example.dreamshops.dto.CartDto;
import com.example.dreamshops.service.Cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(Long id) {
        cartService.clearCart(id);
    }
}
