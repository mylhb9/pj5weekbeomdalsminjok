package com.beom.beomdalsminjok.dto.restaurantdto;

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

}
