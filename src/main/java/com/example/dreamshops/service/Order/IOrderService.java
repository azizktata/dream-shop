package com.example.dreamshops.service.Order;

import com.example.dreamshops.dto.OrderDto;
import com.example.dreamshops.enums.OrderStatus;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    OrderDto removeOrderItem(Long orderId, Long orderItemId);
    List<OrderDto> getOrdersByUserId(Long userId);
    OrderDto getOrderById(Long orderId);
    OrderDto updateOrderStatus(Long orderId, OrderStatus status);
    void deleteOrder(Long orderId);
}
