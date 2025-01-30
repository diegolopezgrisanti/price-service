package com.inditex.price.service.application.findprices;

import com.inditex.price.service.domain.exception.PriceNotFoundException;
import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.domain.database.PriceRepository;
import com.inditex.price.service.domain.usecases.FindPricesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FindPricesUseCaseImpl implements FindPricesUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price execute(Long productId, Long brandId, LocalDateTime dateTime) {

        return priceRepository.findPrices(productId, brandId, dateTime)
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("Price not found for productId: %d and brandId: %d", productId, brandId)
                ));
    }
}