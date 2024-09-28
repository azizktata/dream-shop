package com.example.dreamshops.controller;

import com.example.dreamshops.dto.OrderDto;
import com.example.dreamshops.service.Order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@PathVariable Long userId) {
        return orderService.placeOrder(userId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
