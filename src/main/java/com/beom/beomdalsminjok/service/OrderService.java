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
        // orderRequestDto -> Order
        // 주문할 음식점 생성
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId()).orElseThrow(
                ()->new IllegalArgumentException("fuck you")
        );
        //orderRequestDto를 order화 시키기 위한 파라미터 orderFoods
        List<OrderFood> orderFoods = new ArrayList<>();
        //orderRequestDto의 필드인 List<orderFoodRequestDto>의 요소 값인 orderFoodRequestDto의 id 값을 이용하여 food를 추출하기 위함.
        HashMap<Long, Food> foodMap = new HashMap<>();
        for(OrderFoodRequestDto ofreq : orderRequestDto.getFoods()) {
            Food food = foodRepository.findById(ofreq.getId()).orElseThrow(()->new IllegalArgumentException("fuck you"));
            foodMap.put(ofreq.getId(), food);
        }
        int totalprice = 0;
        // orderFoods를 만들어주고, totalprice를 만들어주는 for 문
        for (int i=0; i<orderRequestDto.getFoods().size(); i++) {
            OrderFoodRequestDto orderfood = orderRequestDto.getFoods().get(i);
            Long id = orderfood.getId();
            int quantity = orderfood.getQuantity();
            Food food = foodMap.get(id);
            orderFoods.add(new OrderFood(food, quantity));
            totalprice += quantity * food.getPrice();
        }
        totalprice = totalprice + restaurant.getDeliveryFee();
//        Order order = orderRequestDto.toEntity(orderFoods,totalprice);
        Order order = new Order(restaurant.getId(), totalprice, restaurant.getDeliveryFee(), orderFoods, restaurant.getMinOrderFee());
        orderRepository.save(order);
        // OrderResponseDto 생성
        List<OrderFoodResponseDto> foodResponseDtos = new ArrayList<>();
        // orderFoodRequestDto의 값을 foodResponseDtos에 담을거니까, for문을 request를 이용해서 돌려줌
        for(OrderFoodRequestDto foodRequestDto : orderRequestDto.getFoods()) {
            //저장된 hashmap 가져다가 사용
            Food food = foodMap.get(foodRequestDto.getId());
            foodResponseDtos.add(new OrderFoodResponseDto(food.getName(), foodRequestDto.getQuantity(), food.getPrice()*foodRequestDto.getQuantity()));
        }
        return new OrderResponseDto(restaurant.getName(), foodResponseDtos, restaurant.getDeliveryFee(), totalprice);
    }
    //주문조회하기 메소드
    @Transactional
    public List<OrderResponseDto> getAllorders() {

        List<Order> orders = orderRepository.findAll();
        HashMap<Long, Restaurant> restaurantHashMap = new HashMap<>();
        HashMap<Long, List<OrderFoodResponseDto>> orderFoodResponseDtoHashMap = new HashMap<>();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for(Order order : orders) {
            //주문에 해당하는 음식점 찾기
            Restaurant restaurant = restaurantRepository.findById(order.getRestaurantId()).orElseThrow(
                    ()->new IllegalArgumentException("fuck you"));
            //주문의 음식점 아이디를 키로하고 음식점을 value로 하여 해시맵 만들기
            restaurantHashMap.put(order.getRestaurantId(), restaurant);
            List<OrderFoodResponseDto> orderFoodResponseDtos = new ArrayList<>();
            //order.getOrderFoods() -> orderFoodResponseDtos
            for(OrderFood orderFood : order.getOrderFoods()) {
                orderFoodResponseDtos.add(orderFood.toOrderFoodResponseDto());
            }
            orderFoodResponseDtoHashMap.put(order.getId(),orderFoodResponseDtos);
            orderResponseDtos.add(new OrderResponseDto(restaurant.getName()
                    , orderFoodResponseDtoHashMap.get(order.getId())
                    , restaurant.getDeliveryFee()
                    , order.getTotalprice()));
        }
        return orderResponseDtos;}

}
