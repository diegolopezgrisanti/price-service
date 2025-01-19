package com.inditex.price.service.application.findprices;

import com.inditex.price.service.domain.Price;
import com.inditex.price.service.domain.PriceRepository;

import java.time.LocalDateTime;
import java.util.List;

public class FindPricesUseCase {

    private final PriceRepository priceRepository;

    public FindPricesUseCase(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price execute(Long productId, Long brandId, LocalDateTime dateTime) {
        List<Price> prices = priceRepository.findPrices(productId, brandId, dateTime);

        return prices.isEmpty() ? null : prices.get(0);
    }
}