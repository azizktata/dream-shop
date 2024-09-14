package com.example.dreamshops.service.Cart;

import com.example.dreamshops.dto.CartDto;
import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.request.CartItemRequest;

import java.util.List;

public interface ICartService {
    void clearCart(Long id);
    CartDto getCart(Long id);
    CartDto removeCartItem(Long cartId, Long itemId);
    CartItemDto addCartItem(CartItemRequest request, Long userId);
    CartItemDto updateCartItem(Integer quantity, Long cartItemId, Long userId);

    List<CartItemDto> getCartItems(Long cartId);
}
