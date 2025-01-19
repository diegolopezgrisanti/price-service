package com.inditex.price.service.infrastructure.config;

import com.inditex.price.service.application.findprices.FindPricesUseCase;
import com.inditex.price.service.domain.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public FindPricesUseCase findPriceUseCase(
            PriceRepository priceRepository
    ) {
        return new FindPricesUseCase(priceRepository);
    }
}
