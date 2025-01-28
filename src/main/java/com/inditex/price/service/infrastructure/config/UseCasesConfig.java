package com.inditex.price.service.infrastructure.config;

import com.inditex.price.service.application.findprices.FindPricesUseCaseImpl;
import com.inditex.price.service.domain.interfaces.PriceRepository;
import com.inditex.price.service.domain.usecases.FindPricesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public FindPricesUseCase findPricesUseCase(
            PriceRepository priceRepository
    ) {
        return new FindPricesUseCaseImpl(priceRepository);
    }
}