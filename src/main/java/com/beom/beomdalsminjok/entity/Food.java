package com.beom.beomdalsminjok.entity;



import com.beom.beomdalsminjok.dto.fooddto.FoodRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderFoodRequestDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderFoodResponseDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="RESTAURNAT_ID")
    private Restaurant restaurant;

    public Food(Restaurant restaurant, FoodRequestDto foodDto) {
        this.name = foodDto.getName();
        this.price = foodDto.getPrice();
        this.restaurant = restaurant;
    }

    @Builder
    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public OrderFoodResponseDto toDto() {
        return OrderFoodResponseDto.builder()
                .name(name)
                .price(price)
                .build();
    }


}
