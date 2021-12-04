package com.beom.beomdalsminjok.entity;


import com.beom.beomdalsminjok.dto.restaurantdto.RestaurantRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int deliveryFee;

    @Column
    private int minOrderFee;

    public Restaurant(RestaurantRequestDto restaurantRequestDto) {
        this.name = restaurantRequestDto.getName();
        this.deliveryFee = restaurantRequestDto.getDeliveryFee();
        this.minOrderFee = restaurantRequestDto.getMinOrderPrice();

    }

}
