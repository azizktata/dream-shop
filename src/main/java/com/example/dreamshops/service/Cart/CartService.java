package com.example.dreamshops.service.Cart;

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
        Cart cart = getCart(id);
        cartRepo.delete(cart);
    }

    @Override
    public Cart getCart(Long id) {
        return cartRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + id + " not found"));
    }


}
