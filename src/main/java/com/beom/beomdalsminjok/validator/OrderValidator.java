package com.beom.beomdalsminjok.validator;


import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public static int validateOrderFoodQuantity(int quantity) {
        if(quantity < 1 || quantity >100) {
            throw new IllegalArgumentException("주문 허용값 1~100임");
        }
        return quantity;
    }

    public static int validateTotalprice(int totalPrice, int deliveryFee, int minOrderPrice) {
        if (totalPrice - deliveryFee < minOrderPrice) {
            throw new IllegalArgumentException("주문음식 가격이 최소 주문 가격을 넘지 않습니다.");
        }
        return totalPrice;
    }



}
