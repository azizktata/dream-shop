package com.example.dreamshops.service.Order;

import com.example.dreamshops.dto.OrderDto;
import com.example.dreamshops.enums.OrderStatus;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.*;
import com.example.dreamshops.repository.OrderItemRepo;
import com.example.dreamshops.repository.OrderRepo;
import com.example.dreamshops.repository.ProductRepo;
import com.example.dreamshops.service.Cart.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;

    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = new Order();
        order.setCreatedDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(cart.getUser());

        Set<CartItem> cartItems = cart.getCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());

            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepo.save(product);

            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        });

        order.setOrderItems(orderItems);
        order.updateTotalAmount();

        cartService.clearCart(cart.getId());
        return OrderDto.toDto(orderRepo.save(order));
    }

    @Override
    public OrderDto removeOrderItem(Long orderId, Long orderItemId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        OrderItem orderItem = orderItemRepo.findById(orderItemId).orElseThrow(() -> new EntityNotFoundException("OrderItem with id: " + orderItemId + " not found"));
        order.removeOrderItem(orderItem);
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
        orderRepo.findById(orderId).ifPresentOrElse(orderRepo::delete, () -> {
            throw new EntityNotFoundException("Order with id: " + orderId + " not found");
        });
    }
}
