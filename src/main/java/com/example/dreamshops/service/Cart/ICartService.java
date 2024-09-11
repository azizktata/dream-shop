package com.example.dreamshops.service.Cart;

import com.example.dreamshops.dto.CartDto;

public interface ICartService {
    void clearCart(Long id);
    CartDto getCart(Long id);
}
