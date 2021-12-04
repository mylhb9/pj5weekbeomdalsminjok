package com.beom.beomdalsminjok.controller;


import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantRequestDto;
import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantResponseDto;
import com.beom.beomdalsminjok.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {
    private final RestaurantService restaurantService;

    //음식점 등록
    @PostMapping("/restaurant/register")
    public RestaurantResponseDto registerRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        return restaurantService.saveRestaurnat(restaurantRequestDto);
    }

    //음식점 조회
    @GetMapping("/restaurants")
    public List<RestaurantResponseDto> getRestaurant(){
        return restaurantService.getAllRestaurants();
    }


}
