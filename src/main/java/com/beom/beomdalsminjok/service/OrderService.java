package com.beom.beomdalsminjok.service;


import com.beom.beomdalsminjok.dto.orderdto.OrderFoodRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderFoodResponseDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
import com.beom.beomdalsminjok.entity.Food;
import com.beom.beomdalsminjok.entity.Order;
import com.beom.beomdalsminjok.entity.OrderFood;
import com.beom.beomdalsminjok.entity.Restaurant;
import com.beom.beomdalsminjok.repository.FoodRepository;
import com.beom.beomdalsminjok.repository.OrderFoodRepository;
import com.beom.beomdalsminjok.repository.OrderRepository;
import com.beom.beomdalsminjok.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderFoodRepository orderFoodRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    //주문하기 메소드
    @Transactional
    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto) {
        // orderRequestDto -> order 저장 -> orderResponseDto
        // restaurant를 repository 에서 가져와서 deliveryFee 가져오기
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId()).orElseThrow(
                ()->new IllegalArgumentException("해당 음식점이 존재하지 않습니다."));
        //List<OrderFood> ordedFoods 만들기
        int totalprice = 0;
        List<OrderFood> orderFoods = new ArrayList<>();
        HashMap<Long, Food> foodHashMap = new HashMap<>();
        for(OrderFoodRequestDto orderFoodRequestDto : orderRequestDto.getFoods()) {
            Food food = foodRepository.findById(orderFoodRequestDto.getId()).orElseThrow(
                    ()->new IllegalArgumentException("해당 음식이 존재하지 않습니다"));
            Long id = orderFoodRequestDto.getId();
            int quantity = orderFoodRequestDto.getQuantity();
            foodHashMap.put(id,food);
            orderFoods.add(new OrderFood(foodHashMap.get(id), quantity));
            totalprice += foodHashMap.get(id).getPrice() * quantity;
        }
        totalprice = totalprice + restaurant.getDeliveryFee();

        Order order = new Order(orderRequestDto.getRestaurantId(), totalprice, restaurant.getDeliveryFee(),orderFoods , restaurant.getMinOrderFee());
        orderRepository.save(order);
        //List<OrderFoodResponseDto> foods 만들기
        List<OrderFoodResponseDto> foods = new ArrayList<>();
        for(OrderFoodRequestDto orderReq : orderRequestDto.getFoods()) {
            Food food = foodHashMap.get(orderReq.getId());
            int quantity = orderReq.getQuantity();
            foods.add(new OrderFoodResponseDto(food.getName(), quantity, food.getPrice() *quantity));
        }
        return new OrderResponseDto(restaurant.getName(), foods, restaurant.getDeliveryFee(), order.getTotalprice());

    }
    //주문조회하기 메소드
    @Transactional
    public List<OrderResponseDto> getAllorders() {
        //List<order> -> List<orderResponseDto>
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        HashMap<Long, List<OrderFoodResponseDto>> foodHashMap = new HashMap<>();
        for (Order order : orders) {
            List<OrderFoodResponseDto> foods = new ArrayList<>();
            Restaurant restaurant = restaurantRepository.findById(order.getRestaurantId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 음식점이 없습니다."));
            for (OrderFood orderFood : order.getOrderFoods()) {
                foods.add(new OrderFoodResponseDto(orderFood.getFood().getName(), orderFood.getQuantity(), orderFood.getPrice()));
            }
            foodHashMap.put(order.getId(), foods);
            orderResponseDtos.add(new OrderResponseDto(restaurant.getName(), foodHashMap.get(order.getId()), restaurant.getDeliveryFee(), order.getTotalprice()));
        }
        return orderResponseDtos;
    }
}