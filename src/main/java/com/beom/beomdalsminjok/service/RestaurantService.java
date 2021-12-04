package com.beom.beomdalsminjok.service;


import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantRequestDto;
import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantResponseDto;
import com.beom.beomdalsminjok.entity.Restaurant;
import com.beom.beomdalsminjok.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    //음식점 등록 서비스
    public RestaurantResponseDto saveRestaurnat(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = new Restaurant(restaurantRequestDto);
        minOrderPriceCheck(restaurant);
        deliveryFeeCheck(restaurant);
        System.out.println(restaurant);
        restaurantRepository.save(restaurant);

        return new RestaurantResponseDto(restaurant);
    }

    //음식점 조회 서비스
    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurant = restaurantRepository.findAll();
        System.out.println(restaurant);
        List<RestaurantResponseDto> restaurantResponseDtos = new ArrayList<>();
        for (int i = 0; i< restaurant.size(); i++) {
            restaurantResponseDtos.add(new RestaurantResponseDto(restaurant.get(i)));
        }
        return restaurantResponseDtos;
    }




    // 음식점 등록 서비스
    // 최소 주문가격 유효성 검사
    private void minOrderPriceCheck(Restaurant restaurant) {
        if (restaurant.getMinOrderFee() < 1000 || restaurant.getMinOrderFee() > 100000) {
            throw new IllegalArgumentException("최소주문 가격이 허용값을 벗어났습니다.");
        }
        if (restaurant.getMinOrderFee()%100 != 0) {
            throw new IllegalArgumentException("최소주문 가격이 100원 단위가 아닙니다");
        }
    }
    // 배달비 유효성 검사
    private void deliveryFeeCheck(Restaurant restaurant) {
        if(restaurant.getDeliveryFee()<0 ||restaurant.getDeliveryFee()>10000) {
            throw new IllegalArgumentException("배달비가 허용값을 벗어났습니다.");
        }
        if(restaurant.getDeliveryFee() % 500 !=0) {
            throw new IllegalArgumentException("배달비가 500원 단위가 아닙니다.");
        }
    }

}
