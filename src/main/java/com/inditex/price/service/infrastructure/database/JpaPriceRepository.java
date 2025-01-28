package com.inditex.price.service.infrastructure.database;

import com.inditex.price.service.domain.interfaces.PriceRepository;
import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.infrastructure.entity.PriceEntity;
import com.inditex.price.service.infrastructure.mappers.PriceMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long>, PriceRepository {

    @Query("""
            SELECT p FROM PriceEntity p
            WHERE p.productId = :productId
            AND p.brandId = :brandId
            AND p.startDate <= :dateTime
            AND p.endDate >= :dateTime
            ORDER BY p.priority DESC
    """)
    List<PriceEntity> findProductPrices(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("dateTime") LocalDateTime dateTime
    );

    @Override
    default List<Price> findPrices(Long productId, Long brandId, LocalDateTime dateTime) {
        List<PriceEntity> entities = findProductPrices(productId, brandId, dateTime);
        return PriceMapper.toDomainList(entities);
    }
}