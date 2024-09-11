package com.example.dreamshops.service.CartItem;

import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.CartItemRepo;
import com.example.dreamshops.repository.CartRepo;
import com.example.dreamshops.repository.ProductRepo;
import com.example.dreamshops.request.CartItemRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemService implements ICartItemService{

    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;

    public CartItemService(CartItemRepo cartItemRepo, CartRepo cartRepo, ProductRepo productRepo) {
        this.cartItemRepo = cartItemRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Override
    public CartItemDto addCartItem(CartItemRequest request) {
        if (request.getCartId() == null){
            Cart cart = new Cart();
            Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product with id: " + request.getProductId() + " not found"));

            CartItem cartItem = new CartItem();
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setTotalPrice();
            cart.getCartItems().add(cartItem);
            cart.setTotal();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            return CartItemDto.toDto(cartItemRepo.save(cartItem));
        }
        else {
            Cart cart = cartRepo.findById(request.getCartId()).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + request.getCartId() + " not found"));
            Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product with id: " + request.getProductId() + " not found"));

            if(cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProduct().getId().equals(product.getId()))){
                throw new AlreadyExistsException("Product already in cart");
            }

            CartItem cartItem = new CartItem();
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setTotalPrice();
            cart.getCartItems().add(cartItem);
            cart.setTotal();
            cartItem.setCart(cart);
            cartItem.setProduct(product);

            return CartItemDto.toDto(cartItemRepo.save(cartItem));
        }
    }

    @Override
    public CartItemDto updateCartItem(CartItemRequest request) {
        Cart cart = cartRepo.findById(request.getCartId()).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + request.getCartId() + " not found"));
        cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(request.getProductId())).findFirst().ifPresent(cartItem -> {
            cartItem.setQuantity(request.getQuantity());
            cartItem.setTotalPrice();
        });
        cart.setTotal();

        return CartItemDto.toDto(cartRepo.save(cart).getCartItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(request.getProductId())).findFirst().orElseThrow(() -> new EntityNotFoundException("Cart item with product id: " + request.getProductId() + " not found")));
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepo.findById(id).ifPresentOrElse(cartItemRepo::delete, () -> {
            throw new EntityNotFoundException("Cart item with id: " + id + " not found");
        });
    }

    @Override
    public List<CartItemDto> getCartItems(Long cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + cartId + " not found"));
        return cart.getCartItems().stream().map(CartItemDto::toDto).toList();
    }
}
