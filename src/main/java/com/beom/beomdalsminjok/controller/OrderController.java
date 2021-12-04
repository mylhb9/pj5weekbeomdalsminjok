package com.beom.beomdalsminjok.controller;


import com.beom.beomdalsminjok.dto.orderdto.OrderRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
import com.beom.beomdalsminjok.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;


    //주문하기
    @PostMapping("/order/request")
    public OrderResponseDto registOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.saveOrder(orderRequestDto);
    }
    //주문조회하기
    @GetMapping("/orders")
    public List<OrderResponseDto> getOrder() {
        return orderService.getAllorders();
    }



}
