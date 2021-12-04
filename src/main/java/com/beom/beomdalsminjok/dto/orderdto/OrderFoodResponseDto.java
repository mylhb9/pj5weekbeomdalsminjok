package com.beom.beomdalsminjok.dto.orderdto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderFoodResponseDto {

    private String name;

    private int quantity;

    private int price;

    @Builder
    public OrderFoodResponseDto(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
