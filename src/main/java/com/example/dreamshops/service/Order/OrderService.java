package com.example.dreamshops.service.Order;

import com.example.dreamshops.dto.OrderDto;
import com.example.dreamshops.dto.OrderItemDto;
import com.example.dreamshops.enums.OrderStatus;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.*;
import com.example.dreamshops.repository.OrderItemRepo;
import com.example.dreamshops.repository.OrderRepo;
import com.example.dreamshops.repository.ProductRepo;
import com.example.dreamshops.repository.UserRepo;
import com.example.dreamshops.request.OrderItemRequest;
import com.example.dreamshops.request.OrderRequest;
import com.example.dreamshops.service.Cart.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;

    @Override
    public OrderDto placeOrder(Long userId, OrderRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
        Order order = new Order();
        order.setCreatedDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        user.addOrder(order);


        List<OrderItem> orderItems = new ArrayList<>();
        request.getOrderItems().stream().map(orderItemDto -> {

            Product product = productRepo.findById(orderItemDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product with id: " + orderItemDto.getProductId() + " not found"));
            OrderItem orderItem = OrderItemRequest.toEntity(orderItemDto, product);
            orderItems.add(orderItem);
            orderItem.setOrder(order);


            product.setInventory(product.getInventory() - orderItemDto.getQuantity());
            product.setNewProductStatus();
            productRepo.save(product);

            return orderItemRepo.save(orderItem);
        }).toList();

        order.setOrderItems(orderItems);
        order.updateTotalAmount();
        userRepo.save(user);

        return OrderDto.toDto(orderRepo.save(order));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepo.findByUserId(userId).stream().map(OrderDto::toDto).toList();
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepo.findAll().stream().map(OrderDto::toDto).toList();
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepo.findById(orderId).map(OrderDto::toDto).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
        return orderRepo.findById(orderId).map(order -> {
            order.setStatus(status);
            return OrderDto.toDto(orderRepo.save(order));
        }).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        order.clearOrder();
        orderRepo.delete(order);
    }
}
