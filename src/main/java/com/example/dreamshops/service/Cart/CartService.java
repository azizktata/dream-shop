package com.example.dreamshops.service.Cart;

import com.example.dreamshops.dto.CartDto;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.repository.CartRepo;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService{

    private final CartRepo cartRepo;

    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }


    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + id + " not found"));
        cartRepo.delete(cart);
    }

    @Override
    public CartDto getCart(Long id) {
        return CartDto.toDto(cartRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + id + " not found")));
    }


}
