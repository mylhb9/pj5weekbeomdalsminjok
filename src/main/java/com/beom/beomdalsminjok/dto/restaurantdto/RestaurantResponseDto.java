package com.beom.beomdalsminjok.dto.restaurantdto;


import com.beom.beomdalsminjok.entity.Restaurant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
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
    @Builder
    public RestaurantResponseDto(Long id, String name, int minOrderPrice, int deliveryFee) {
        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }

}
