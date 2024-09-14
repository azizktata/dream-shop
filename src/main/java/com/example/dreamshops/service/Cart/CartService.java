package com.example.dreamshops.service.Cart;

import com.example.dreamshops.dto.CartDto;
import com.example.dreamshops.dto.CartItemDto;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.CartItem;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.CartItemRepo;
import com.example.dreamshops.repository.CartRepo;
import com.example.dreamshops.repository.ProductRepo;
import com.example.dreamshops.repository.UserRepo;
import com.example.dreamshops.request.CartItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService implements ICartService{

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public Cart initilizeCart(Long userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));

        return Optional.ofNullable(cartRepo.findByUserId(userId)).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepo.save(cart);
        });

    }

    public Cart getCartByUserId(Long userId) {
        return Optional.ofNullable(cartRepo.findByUserId(userId)).orElseThrow(() -> new EntityNotFoundException("Cart for user with id: " + userId + " not found"));
    }
    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + id + " not found"));
        cart.clearCartItems();
        cartRepo.save(cart);
    }

    @Override
    public CartDto getCart(Long id) {
        return CartDto.toDto(cartRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart with id: " + id + " not found")));
    }

    @Override
    public CartDto removeCartItem(Long userId, Long itemId) {
        Cart cart = cartRepo.findByUserId(userId);
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("CartItem with id: " + itemId + " not found"));
        cart.removeCartItem(cartItem);
        return CartDto.toDto(cartRepo.save(cart));
    }

    @Override
    public CartItemDto addCartItem(CartItemRequest request, Long userId) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product with id: " + request.getProductId() + " not found"));
        Cart cart = initilizeCart(userId);


        if (request.getQuantity() > product.getInventory()){
            throw new IllegalArgumentException("Exceeded inventory limit");
        }

        if(cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProduct().getId().equals(product.getId()))){
            throw new AlreadyExistsException("Product already in cart");
        }

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(request.getQuantity());
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice();
        cartItem.setProduct(product);

        cart.addCartItem(cartItem);

        return CartItemDto.toDto(cartRepo.save(cart).getCartItems().stream().filter(item -> item.getProduct().getId().equals(request.getProductId())).findFirst().orElseThrow(() -> new EntityNotFoundException("Cart item with product id: " + request.getProductId() + " not found")));

    }

    @Override
    public CartItemDto updateCartItem(Integer quantity, Long cartItemId, Long userId) {

        Cart cart = cartRepo.findByUserId(userId);
        cart.getCartItems().stream().filter(cartItem -> cartItem.getId().equals(cartItemId)).findFirst().ifPresentOrElse(cartItem -> {
        if (cartItem.getQuantity() > cartItem.getProduct().getInventory()){
            throw new IllegalArgumentException("Exceeded inventory limit");
        }

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        },() -> new EntityNotFoundException("Cart item with id: " + cartItemId + " not found"));

        cart.setTotal();

        return CartItemDto.toDto(cartRepo.save(cart).getCartItems().stream().filter(cartItem -> cartItem.getId().equals(cartItemId)).findFirst().orElseThrow(() -> new EntityNotFoundException("Cart item " + cartItemId + " not found")));
    }

    @Override
    public List<CartItemDto> getCartItems(Long userId) {
        Cart cart = Optional.ofNullable(cartRepo.findByUserId(userId)).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        return cart.getCartItems().stream().map(CartItemDto::toDto).toList();
    }


    public CartDto getCartByUser(Long id) {
        return CartDto.toDto(cartRepo.findByUserId(id));
    }
}
