package com.beom.beomdalsminjok.entity;


import com.beom.beomdalsminjok.dto.orderdto.OrderFoodResponseDto;
import com.beom.beomdalsminjok.dto.orderdto.OrderResponseDto;
import com.beom.beomdalsminjok.validator.OrderValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@NoArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long restaurantId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDERFOOD_ID")
    private List<OrderFood> orderFoods;

    @Column
    private int totalprice;

    @Builder
    public Order(Long restaurantId, int totalprice, int deliveryFee, List<OrderFood> orderFoods, int minOrderPrice) {
        this.restaurantId = restaurantId;
        this.totalprice = OrderValidator.validateTotalprice(totalprice, deliveryFee, minOrderPrice);
        this.orderFoods = orderFoods;
    }





}
