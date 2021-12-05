package com.beom.beomdalsminjok.service;


import com.beom.beomdalsminjok.dto.fooddto.FoodRequestDto;
import com.beom.beomdalsminjok.dto.fooddto.FoodResponseDto;
import com.beom.beomdalsminjok.entity.Food;
import com.beom.beomdalsminjok.entity.Restaurant;
import com.beom.beomdalsminjok.repository.FoodRepository;
import com.beom.beomdalsminjok.repository.RestaurantRepository;
import com.beom.beomdalsminjok.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    //음식 등록 서비스
    public void saveFood(Long id, List<FoodRequestDto> foodDtoList) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 음식점이 없습니다")
        );
        //음식점과 일치하는 음식 리스트
        List<Food> foodList  = foodRepository.findByRestaurant_Id(id);
        // 같은 음식이름 유효성 검사
        List<String> foodnames = new ArrayList<>();
        List<String> foodDtonames = new ArrayList<>();
        for( int i =0; i< foodList.size(); i++) {
            foodnames.add(foodList.get(i).getName());
        }
        for( int i =0; i< foodDtoList.size(); i++) {
            foodDtonames.add(foodDtoList.get(i).getName());
        }
        FoodValidator.checkFood1(foodDtoList, foodnames);
        FoodValidator.checkFood2(foodDtonames);
        //해당 음식점에 해당하는 음식 리스트 만들기
        List<Food> foods = new ArrayList<>();
        for (int i=0; i<foodDtoList.size(); i++) {
            foods.add(new Food(restaurant,foodDtoList.get(i)));
        }
        foodRepository.saveAll(foods);
    }
    //음식조회 서비스
    public List<FoodResponseDto> getAllFoods(Long id) {
        List<FoodResponseDto> foods = new ArrayList<>();
        for (int i=0; i<foodRepository.findByRestaurant_Id(id).size(); i++) {
            foods.add(new FoodResponseDto(foodRepository.findByRestaurant_Id(id).get(i)));
        }
        return foods;
    }
}
