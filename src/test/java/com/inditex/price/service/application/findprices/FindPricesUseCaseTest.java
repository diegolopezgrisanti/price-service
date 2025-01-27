package com.inditex.price.service.application.findprices;

import com.inditex.price.service.domain.Brand;
import com.inditex.price.service.domain.Price;
import com.inditex.price.service.domain.PriceRepository;
import com.inditex.price.service.domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindPricesUseCaseTest {

    private final PriceRepository priceRepository = mock(PriceRepository.class);
    private final FindPricesUseCase findPricesUseCase = new FindPricesUseCase(priceRepository);

    Long productId = 35455L;
    Long brandId = 1L;
    LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0, 0);

    @Test
    void shouldReturnPriceWhenListIsNotEmpty() {
        // GIVEN
        Price expectedPrice = new Price(
                1L,
                new Brand(brandId),
                LocalDateTime.of(2020, 6, 14, 0,0,0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                new Product(productId),
                0,
                BigDecimal.valueOf(35.50),
                Currency.getInstance("EUR")
        );
        when(priceRepository.findPrices(
                productId, brandId, dateTime)).thenReturn(List.of(expectedPrice));

        // WHEN
        Price result = findPricesUseCase.execute(productId, brandId, dateTime);

        // THEN
        assertNotNull(result);
        assertEquals(expectedPrice, result);

        assertEquals(brandId, result.getBrand().getId());
        assertEquals(Integer.valueOf(1), result.getPriceList());
        assertEquals(productId, result.getProduct().getId());
        assertEquals(Integer.valueOf(0), result.getPriority());
        assertEquals(BigDecimal.valueOf(35.50), result.getFinalPrice());
        assertEquals("EUR", result.getCurrency().getCurrencyCode());

        verify(priceRepository, times(1)).findPrices(
                productId, brandId, dateTime);
    }

    @Test
    void shouldReturnNullWhenPriceListIsEmpty() {
        // GIVEN
        when(priceRepository.findPrices(
                productId, brandId, dateTime)).thenReturn(List.of());

        // WHEN
        Price result = findPricesUseCase.execute(productId, brandId, dateTime);

        // THEN
        assertNull(result);

        verify(priceRepository, times(1)).findPrices(productId, brandId, dateTime);

    }

}