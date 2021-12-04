package com.beom.beomdalsminjok.repository;

import com.beom.beomdalsminjok.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food>findByRestaurant_Id(Long id);
}
