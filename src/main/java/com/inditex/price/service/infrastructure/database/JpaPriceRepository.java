package com.inditex.price.service.infrastructure.database;

import com.inditex.price.service.domain.Price;
import com.inditex.price.service.domain.PriceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<Price, Long>, PriceRepository {

    @Query("""
            SELECT p FROM Price p
            WHERE p.product.id = :productId
            AND p.brand.id = :brandId
            AND p.startDate <= :dateTime
            AND p.endDate >= :dateTime
            ORDER BY p.priority DESC
    """)
    List<Price> findProductPrices(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("dateTime") LocalDateTime dateTime
    );

    @Override
    default List<Price> findPrices(Long productId, Long brandId, LocalDateTime dateTime) {
        return findProductPrices(productId, brandId, dateTime);
    }
}