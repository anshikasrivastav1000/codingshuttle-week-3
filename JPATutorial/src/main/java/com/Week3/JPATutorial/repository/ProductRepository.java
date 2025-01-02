package com.Week3.JPATutorial.repository;

import com.Week3.JPATutorial.entities.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByTitle(String title, Pageable pageable);

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);

    List<ProductEntity> findByQuantityAndPrice(int quantity, BigDecimal price);
    List<ProductEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
