package com.beom.beomdalsminjok.dto.orderdto;


import com.beom.beomdalsminjok.entity.Order;
import com.beom.beomdalsminjok.entity.OrderFood;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderRequestDto {

    private Long restaurantId;

    private List<OrderFoodRequestDto> foods;

    @Builder
    public OrderRequestDto(Long restaurantId,List<OrderFoodRequestDto> foods) {
        this.restaurantId = restaurantId;
        this.foods = foods;
    }

    public Order toEntity(List<OrderFood> orderFoods, int totalprice) {
        return Order.builder()
                .orderFoods(orderFoods)
                .restaurantId(restaurantId)
                .totalprice(totalprice)
                .build();


    }

}
