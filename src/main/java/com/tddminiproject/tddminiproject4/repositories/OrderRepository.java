package com.tddminiproject.tddminiproject4.repositories;

import com.tddminiproject.tddminiproject4.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteById(Long id);

}

