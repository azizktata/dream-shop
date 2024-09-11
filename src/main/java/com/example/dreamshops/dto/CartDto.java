package com.example.dreamshops.dto;

import com.example.dreamshops.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private BigDecimal total;
    private Set<CartItemDto> cartItems;

    public static CartDto toDto(Cart entity){
        return new CartDto(entity.getId(), entity.getTotal(), entity.getCartItems().stream().map(CartItemDto::toDto).collect(java.util.stream.Collectors.toSet()));
    }
}
