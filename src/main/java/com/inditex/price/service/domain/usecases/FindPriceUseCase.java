package com.inditex.price.service.domain.usecases;

import com.inditex.price.service.domain.models.Price;

import java.time.LocalDateTime;

public interface FindPriceUseCase {
    Price execute(Long productId, Long brandId, LocalDateTime dateTime);
}