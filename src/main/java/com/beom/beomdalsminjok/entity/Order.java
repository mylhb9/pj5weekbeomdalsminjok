package com.beom.beomdalsminjok.entity;


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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="RESTAURANT_ID")
    private Restaurant restaurant;

    @Column
    private int totalprice;

    @OneToMany(mappedBy = "order")
    List<OrderFood> orderFoods = new ArrayList<>();

    public Order(Restaurant restaurant, int totalprice) {
        this.restaurant = restaurant;
        this.totalprice = totalprice;
    }



}
