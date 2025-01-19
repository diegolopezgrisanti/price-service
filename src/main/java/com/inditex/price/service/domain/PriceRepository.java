package com.inditex.price.service.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {

    List<Price> findPrices(
            Long productId,
            Long brandId,
            LocalDateTime dateTime
    );
}