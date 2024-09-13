package com.example.dreamshops.dto;

import com.example.dreamshops.model.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private Double price;

    public static OrderItemDto toDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setProductPrice(orderItem.getProduct().getPrice().doubleValue());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice().doubleValue());
        return orderItemDto;
    }
}
