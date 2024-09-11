package com.example.dreamshops.service.Cart;

import com.example.dreamshops.model.Cart;

public interface ICartService {
    void clearCart(Long id);
    Cart getCart(Long id);
}
