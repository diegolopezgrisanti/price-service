package com.inditex.price.service.domain.database;

import com.inditex.price.service.domain.models.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findPrices(Long productId, Long brandId, LocalDateTime dateTime);
}