package com.inditex.price.service.application.findprices;

import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.domain.interfaces.PriceRepository;
import com.inditex.price.service.domain.usecases.FindPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPricesUseCaseImpl implements FindPriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price execute(Long productId, Long brandId, LocalDateTime dateTime) {
        List<Price> price = priceRepository.findPrices(productId, brandId, dateTime);

        return price.isEmpty() ? null : price.getFirst();
    }
}