package com.inditex.price.service.infrastructure.entrypoint.rest;

import com.inditex.price.service.domain.exception.InvalidDateFormatException;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.PriceResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceControllerIntegrationTest {

    @Autowired
    private PriceController priceController;

    @Test
    void shouldReturnPriceWhenValidInput() {
        // GIVEN
        Long productId = 35455L;
        Long brandId = 1L;
        String dateTime = "2020-06-14T10:00:00";

        PriceResponseDTO expectedResponse = new PriceResponseDTO();
        expectedResponse.setProductId(35455L);
        expectedResponse.setBrandId(1L);
        expectedResponse.setPriceList(1);
        expectedResponse.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        expectedResponse.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        expectedResponse.setFinalPrice(BigDecimal.valueOf(35.50));
        expectedResponse.setCurrency(Currency.getInstance("EUR"));

        // WHEN
        ResponseEntity<PriceResponseDTO> responseEntity = priceController.getPrices(productId, brandId, dateTime);
        PriceResponseDTO actualResponse = responseEntity.getBody();

        // THEN
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualResponse).isNotNull();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("finalPrice")
                .isEqualTo(expectedResponse);
        assertThat(actualResponse.getFinalPrice().stripTrailingZeros())
                .isEqualTo(expectedResponse.getFinalPrice().stripTrailingZeros());
    }

    @Test
    void shouldReturnBadRequestWhenDateFormatIsInvalid() {
        // GIVEN
        long productId = 35455L;
        long brandId = 1L;
        String invalidDateTime = "invalid-date-format";

        // WHEN & THEN
        InvalidDateFormatException exception = assertThrows(
                InvalidDateFormatException.class,
                () -> priceController.getPrices(productId, brandId, invalidDateTime)
        );

        assertEquals("Invalid date format: " + invalidDateTime, exception.getMessage());
    }
}