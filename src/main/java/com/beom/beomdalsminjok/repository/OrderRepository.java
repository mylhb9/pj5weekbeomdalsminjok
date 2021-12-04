package com.beom.beomdalsminjok.repository;

import com.beom.beomdalsminjok.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
