package com.beom.beomdalsminjok.dto.fooddto;

import com.beom.beomdalsminjok.entity.Food;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodResponseDto {

    private Long id;

    private String name;

    private int price;




    public FoodResponseDto(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.price = food.getPrice();
    }
    public Food toDto() {
        return Food.builder()
                .name(name)
                .price(price)
                .build();
    }

}
