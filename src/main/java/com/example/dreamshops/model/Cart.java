package com.example.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal total;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addCartItem(CartItem cartItem) {
        this.getCartItems().add(cartItem);
        cartItem.setCart(this);
        this.setTotal();
    }

    public void setTotal() {
        this.total = this.cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void removeCartItem(CartItem cartItem) {
        this.getCartItems().remove(cartItem);
        cartItem.setCart(null);
        this.setTotal();
    }

    public void clearCartItems() {
        this.cartItems.clear();
        this.setTotal();
    }
}
