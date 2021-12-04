package com.beom.beomdalsminjok.repository;

import com.beom.beomdalsminjok.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
