package com.example.dreamshops.service.Order;

import com.example.dreamshops.dto.OrderDto;
import com.example.dreamshops.enums.OrderStatus;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    List<OrderDto> getOrdersByUserId(Long userId);
    List<OrderDto> getOrders();
    OrderDto getOrderById(Long orderId);
    OrderDto updateOrderStatus(Long orderId, OrderStatus status);
    void deleteOrder(Long orderId);
}
