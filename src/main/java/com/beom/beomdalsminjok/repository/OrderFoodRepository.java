package com.beom.beomdalsminjok.repository;


import com.beom.beomdalsminjok.entity.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderFoodRepository extends JpaRepository<OrderFood, Long> {
}
