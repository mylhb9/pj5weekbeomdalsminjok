package com.beom.beomdalsminjok.dto.orderdto;


import com.beom.beomdalsminjok.entity.Order;
import com.beom.beomdalsminjok.entity.OrderFood;
import lombok.*;

import java.util.List;
@ToString
@NoArgsConstructor
@Setter
@Getter
public class OrderResponseDto {

    private String restaurantName;

    private List<OrderFoodResponseDto> foods;

    private int deliveryFee;

    private int totalPrice;
    @Builder
    public OrderResponseDto(String restaurantName, List<OrderFoodResponseDto> foods, int deliveryFee, int totalPrice) {
        this.restaurantName = restaurantName;
        this.foods = foods;
        this.deliveryFee =deliveryFee;
        this.totalPrice = totalPrice;
    }


}
