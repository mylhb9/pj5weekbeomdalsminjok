package com.beom.beomdalsminjok.validator;

import com.beom.beomdalsminjok.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantValidator {


    public static void minOrderPriceCheck(Restaurant restaurant) {
        if (restaurant.getMinOrderFee() < 1000 || restaurant.getMinOrderFee() > 100000) {
            throw new IllegalArgumentException("최소주문 가격이 허용값을 벗어났습니다.");
        }
        if (restaurant.getMinOrderFee()%100 != 0) {
            throw new IllegalArgumentException("최소주문 가격이 100원 단위가 아닙니다");
        }
    }
    // 배달비 유효성 검사
    public static void deliveryFeeCheck(Restaurant restaurant) {
        if(restaurant.getDeliveryFee()<0 ||restaurant.getDeliveryFee()>10000) {
            throw new IllegalArgumentException("배달비가 허용값을 벗어났습니다.");
        }
        if(restaurant.getDeliveryFee() % 500 !=0) {
            throw new IllegalArgumentException("배달비가 500원 단위가 아닙니다.");
        }
    }


}
