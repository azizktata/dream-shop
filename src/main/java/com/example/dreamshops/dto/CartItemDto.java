package com.example.dreamshops.dto;

import com.example.dreamshops.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private int quantity;
    private ProductDto product;
    private double unitPrice;
    private double totalPrice;

    public static CartItemDto toDto(CartItem entity){
        return new CartItemDto(entity.getId(), entity.getQuantity(), ProductDto.toDto(entity.getProduct()), entity.getUnitPrice().doubleValue(), entity.getTotalPrice().doubleValue());
    }
}
