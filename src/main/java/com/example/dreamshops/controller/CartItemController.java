package com.example.dreamshops.controller;

import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.request.CartItemRequest;
import com.example.dreamshops.service.CartItem.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartItems")
@AllArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping("/cart/{id}")
    public List<CartItemDto> getCartItems(@PathVariable Long id) {
        return cartItemService.getCartItems(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto addCartItem(@RequestBody CartItemRequest request) {
        return cartItemService.addCartItem(request);
    }

    @PutMapping
    public void updateCartItem(@RequestBody CartItemRequest request) {
        cartItemService.updateCartItem(request);
    }



}
