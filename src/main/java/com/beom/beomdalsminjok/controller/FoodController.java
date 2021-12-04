package com.beom.beomdalsminjok.controller;


import com.beom.beomdalsminjok.dto.fooddto.FoodRequestDto;
import com.beom.beomdalsminjok.dto.fooddto.FoodResponseDto;
import com.beom.beomdalsminjok.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    //음식 등록
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registFood(@PathVariable("restaurantId") Long id, @RequestBody List<FoodRequestDto> foodDtoList) {
        foodService.saveFood(id, foodDtoList);
    }
    //음식 조회
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<FoodResponseDto> getFoods(@PathVariable("restaurantId") Long id) {
        return foodService.getAllFoods(id);
    }

}
