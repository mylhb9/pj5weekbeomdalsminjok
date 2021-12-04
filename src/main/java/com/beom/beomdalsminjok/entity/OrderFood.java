package com.beom.beomdalsminjok.entity;


import com.beom.beomdalsminjok.dto.orderdto.OrderFoodResponseDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class OrderFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @Column
    private int price;

    @Column
    private int quantity;
    @Builder
    public OrderFood(Order order, Food food, int quantity) {
        this.quantity = quantity;
        this.food = food;
        this.order = order;
        this.price = quantity * food.getPrice();

    }


    public OrderResponseDto toOrderResponseDto(Order order,List< OrderFoodResponseDto> orderFoodResponseDto) {
        return OrderResponseDto.builder()
                .restaurantName(order.getRestaurant().getName())
                .foods(orderFoodResponseDto)
                .deliveryFee(order.getRestaurant().getDeliveryFee())
                .totalPrice(order.getTotalprice())
                .build();
    }

}
