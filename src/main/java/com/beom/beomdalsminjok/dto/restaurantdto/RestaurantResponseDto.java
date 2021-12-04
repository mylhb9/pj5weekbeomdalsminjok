package com.beom.beomdalsminjok.dto.restaurantdto;


import com.beom.beomdalsminjok.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantResponseDto {
    private Long id;

    private String name;

    private int minOrderPrice;

    private int deliveryFee;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.minOrderPrice = restaurant.getMinOrderFee();
        this.deliveryFee = restaurant.getDeliveryFee();
    }
}
