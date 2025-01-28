package com.inditex.price.service.infrastructure.database;

import com.inditex.price.service.domain.database.PriceRepository;
import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.infrastructure.entity.PriceEntity;
import com.inditex.price.service.infrastructure.mappers.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;
    private final PriceMapper priceMapper;

    @Override
    public List<Price> findPrices(Long productId, Long brandId, LocalDateTime dateTime) {
        Optional<PriceEntity> entityOptional = jpaPriceRepository.findProductPrices(productId, brandId, dateTime);
        return entityOptional
                .map(priceMapper::toDomain)
                .map(List::of)
                .orElseGet(List::of);
    }
}