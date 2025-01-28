package com.inditex.price.service.infrastructure.database;

import com.inditex.price.service.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT p FROM PriceEntity p
            WHERE p.productId = :productId
            AND p.brandId = :brandId
            AND p.startDate <= :dateTime
            AND p.endDate >= :dateTime
            ORDER BY p.priority DESC
            LIMIT 1
    """)
    Optional<PriceEntity> findProductPrices(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("dateTime") LocalDateTime dateTime
    );
}