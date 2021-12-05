package com.beom.beomdalsminjok.entity;


import com.beom.beomdalsminjok.dto.orderdto.OrderFoodResponseDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
import com.beom.beomdalsminjok.validator.OrderValidator;
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

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ORDER_ID")
//    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @Column
    private int price;

    @Column
    private int quantity;
    @Builder
    public OrderFood(Food food, int quantity) {
        this.quantity = OrderValidator.validateOrderFoodQuantity(quantity);
        this.food = food;
        this.price = OrderValidator.validateOrderFoodQuantity(quantity) * food.getPrice();

    }
    public OrderFoodResponseDto toOrderFoodResponseDto() {
        return OrderFoodResponseDto.builder()
                .name(food.getName())
                .quantity(quantity)
                .price(price)
                .build();
    }



}
