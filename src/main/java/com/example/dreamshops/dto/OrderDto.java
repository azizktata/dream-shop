package com.example.dreamshops.dto;

import com.example.dreamshops.enums.OrderStatus;
import com.example.dreamshops.model.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate createdDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDto> orderItems;

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setCreatedDate(order.getCreatedDate());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderItems(order.getOrderItems().stream().map(OrderItemDto::toDto).collect(Collectors.toList()));
        return orderDto;
    }
}
