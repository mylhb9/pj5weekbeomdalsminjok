package com.beom.beomdalsminjok.service;


import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantRequestDto;
import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantResponseDto;
import com.beom.beomdalsminjok.entity.Restaurant;
import com.beom.beomdalsminjok.repository.RestaurantRepository;
import com.beom.beomdalsminjok.validator.FoodValidator;
import com.beom.beomdalsminjok.validator.RestaurantValidator;
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
        Restaurant restaurant = restaurantRequestDto.toEntity();
        RestaurantValidator.minOrderPriceCheck(restaurant);
        RestaurantValidator.deliveryFeeCheck(restaurant);
        return restaurantRepository.save(restaurant).toDto();
    }

    //음식점 조회 서비스
    public List<RestaurantResponseDto> getAllRestaurants() {
        List<Restaurant> restaurant = restaurantRepository.findAll();
        List<RestaurantResponseDto> restaurantResponseDtos = new ArrayList<>();
        for (int i = 0; i< restaurant.size(); i++) {
            restaurantResponseDtos.add(restaurant.get(i).toDto());
        }
        return restaurantResponseDtos;
    }
}
