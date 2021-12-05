package com.beom.beomdalsminjok.dto.restaurantdto;

import com.beom.beomdalsminjok.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class RestaurantRequestDto {
    private Long id;

    private String name;

    private int minOrderPrice;

    private int deliveryFee;


    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .deliveryFee(deliveryFee)
                .minOrderFee(minOrderPrice)
                .build();
    }



}
