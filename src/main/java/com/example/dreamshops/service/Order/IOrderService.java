package com.example.dreamshops.service.Order;

import com.example.dreamshops.dto.OrderDto;
import com.example.dreamshops.enums.OrderStatus;
import com.example.dreamshops.request.OrderRequest;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId, OrderRequest request);
    List<OrderDto> getOrdersByUserId(Long userId);
    List<OrderDto> getOrders();
    OrderDto getOrderById(Long orderId);
    OrderDto updateOrderStatus(Long orderId, OrderStatus status);
    void deleteOrder(Long orderId);
}
