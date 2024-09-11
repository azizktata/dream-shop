package com.example.dreamshops.service.CartItem;

import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import com.example.dreamshops.request.CartItemRequest;

import java.util.List;

public interface ICartItemService {
    CartItem addCartItem(CartItemRequest request);
    Cart updateCartItem(CartItemRequest request);
    void deleteCartItem(Long id);
    List<CartItem> getCartItems(Long cartId);
}
