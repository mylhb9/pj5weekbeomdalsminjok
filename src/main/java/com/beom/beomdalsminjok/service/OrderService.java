package com.beom.beomdalsminjok.service;


import com.beom.beomdalsminjok.dto.orderdto.OrderFoodResponseDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
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
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId()).orElseThrow(
                ()->new IllegalArgumentException("주문서에 해당하는 음식점이 없습니다.")
        );

        int totalprice = 0;
        for(int i=0; i<orderRequestDto.getFoods().size(); i++) {
            totalprice += orderRequestDto.getFoods().get(i).getQuantity() * foodRepository.findById(orderRequestDto.getFoods().get(i).getId()).orElseThrow(
                    ()-> new IllegalArgumentException("해당음식이 존재하지 않습니다")
            ).getPrice();
        }
        if (totalprice < restaurant.getMinOrderFee()) {
            throw new IllegalArgumentException("음식점의 최소 주문 가격 미만입니다.");
        }
        totalprice = totalprice + restaurant.getDeliveryFee();

        Order order = new Order(restaurant,totalprice);
        List<OrderFood> orderFoods = new ArrayList<>();
        for(int i=0; i<orderRequestDto.getFoods().size(); i++) {
            orderFoods.add(new OrderFood(order, foodRepository.getById(orderRequestDto.getFoods().get(i).getId()), orderRequestDto.getFoods().get(i).getQuantity()));
        }
        System.out.println(orderFoods);
        orderFoodRepository.saveAll(orderFoods);

        System.out.println(orderFoods);
        List<OrderFoodResponseDto> orderFoodResponseDtos = new ArrayList<>();
        //음식점의 최소주문 가격 미만시 에러


        for (int i=0; i<orderRequestDto.getFoods().size(); i++) {
            //음식 주문 수량 에러
            if (orderRequestDto.getFoods().get(i).getQuantity() < 1 || orderRequestDto.getFoods().get(i).getQuantity() > 100) {
                throw new IllegalArgumentException("음식주문수량이 허용값을 벗어났습니다.");
            }

            orderFoodResponseDtos.add(new OrderFoodResponseDto(orderFoods.get(i).getFood().getName(),
                    orderRequestDto.getFoods().get(i).getQuantity(),
                    foodRepository.findById(orderRequestDto.getFoods().get(i).getId()).orElseThrow(
                    ()-> new IllegalArgumentException("해당음식이 존재하지 않습니다")).getPrice() * orderRequestDto.getFoods().get(i).getQuantity()));
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto(restaurant.getName(),orderFoodResponseDtos,restaurant.getDeliveryFee(),totalprice);

        return orderResponseDto;

    }
    //주문조회하기 메소드
    @Transactional
    public List<OrderResponseDto> getAllorders() {
        List<Order> orders = orderRepository.findAll();

        List<OrderFood> orderFoods = orderFoodRepository.findAll();
        List<List<OrderFoodResponseDto>> orderFoodResponseDtoss = new ArrayList<>();
        for(int i=0; i<orders.size(); i++) {
            List<OrderFoodResponseDto> orderFoodResponseDtos = new ArrayList<>();
            for (int j=0; j<orders.get(i).getOrderFoods().size(); j++) {
                orderFoodResponseDtos.add(new OrderFoodResponseDto(
                        orders.get(i).getOrderFoods().get(j).getFood().getName(),
                        orders.get(i).getOrderFoods().get(j).getQuantity(),
                        orders.get(i).getOrderFoods().get(j).getPrice()
                ));
            }
            orderFoodResponseDtoss.add(orderFoodResponseDtos);
        }
        System.out.println(orderFoodResponseDtoss);
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (int i=0; i<orders.size(); i++) {
            Order order = orderRepository.findById(Long.valueOf(i+1)).orElseThrow(()->new IllegalArgumentException("해당하는 주문이 없습니다."));
            orderResponseDtos.add(orderFoods.get(i).toOrderResponseDto(orders.get(i), orderFoodResponseDtoss.get(i)));
        }
        System.out.println(orderResponseDtos);
        return orderResponseDtos;
    }

}
