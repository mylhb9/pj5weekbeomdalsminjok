package com.beom.beomdalsminjok.validator;

import com.beom.beomdalsminjok.dto.fooddto.FoodRequestDto;
import com.beom.beomdalsminjok.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodValidator {
    public static void checkFood1(List<FoodRequestDto> foodDtoList, List<String> foodnames) {
        for (int i = 0; i< foodDtoList.size(); i++) {
            if(foodnames.contains(foodDtoList.get(i).getName())) {
                throw new IllegalArgumentException("해당 음식점에 이미 같은 음식 이름이 존재합니다.");
            }
            if (foodDtoList.get(i).getPrice() < 100 || foodDtoList.get(i).getPrice() > 1000000) {
                throw new IllegalArgumentException("최소주문 가격이 허용값을 벗어났습니다.");
            }
            if (foodDtoList.get(i).getPrice()%100 != 0) {
                throw new IllegalArgumentException("최소주문 가격이 100원 단위가 아닙니다");
            }
        }
    }

    public static void checkFood2(List<String> foodDtonames) {
        for (int i = 0; i< foodDtonames.size()- 1; i++) {
            for (int j = i+1; j< foodDtonames.size(); j++) {
                if(foodDtonames.get(i).equals(foodDtonames.get(j))) {
                    throw new IllegalArgumentException("해당 음식점에 이미 같은 음식 이름이 존재합니다.");
                }
            }
        }
    }

}
