package com.example.dreamshops.service.CartItem;

import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.request.CartItemRequest;

import java.util.List;

public interface ICartItemService {
    CartItemDto addCartItem(CartItemRequest request);
    CartItemDto updateCartItem(CartItemRequest request);
    void deleteCartItem(Long id);
    List<CartItemDto> getCartItems(Long cartId);
}
