package com.gallery.backend.repository;

import com.gallery.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // 이런게 정말 되나? 나는 보고 바로 떠오르지도 않는데...
    List<Order> findByMemberIdOrderByIdDesc(int memberId);
}
