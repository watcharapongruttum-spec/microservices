package com.example.order.service;

import com.example.order.dto.*;
import com.example.order.entity.*;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            // เรียก Product Service เพื่อดึงข้อมูลและตัด stock
            Map product = restTemplate.getForObject(
                "http://localhost:8082/api/products/" + itemReq.getProductId(), Map.class);

            if (product == null) throw new RuntimeException("Product not found");

            restTemplate.postForObject(
                "http://localhost:8082/api/products/" + itemReq.getProductId() 
                + "/deduct?quantity=" + itemReq.getQuantity(), null, Map.class);

            return OrderItem.builder()
                    .productId(itemReq.getProductId())
                    .productName((String) product.get("name"))
                    .quantity(itemReq.getQuantity())
                    .price(new BigDecimal(product.get("price").toString()))
                    .build();
        }).toList();

        BigDecimal total = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .username(request.getUsername())
                .totalAmount(total)
                .items(items)
                .build();

        items.forEach(i -> i.setOrder(order));
        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    public List<OrderResponse> getMyOrders(String username) {
        return orderRepository.findByUsername(username)
                .stream().map(this::toResponse).toList();
    }

    private OrderResponse toResponse(Order order) {
        List<OrderResponse.ItemDetail> details = order.getItems().stream()
                .map(i -> OrderResponse.ItemDetail.builder()
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .username(order.getUsername())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(details)
                .build();
    }
}