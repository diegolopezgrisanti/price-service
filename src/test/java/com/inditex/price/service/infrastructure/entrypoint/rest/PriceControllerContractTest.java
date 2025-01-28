package com.inditex.price.service.infrastructure.entrypoint.rest;

import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.domain.usecases.FindPricesUseCase;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.PriceResponseDTO;
import com.inditex.price.service.infrastructure.mappers.PriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(PriceController.class)
class PriceControllerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindPricesUseCase findPricesUseCase;

    @MockBean
    private PriceMapper priceMapper;

    @Test
    void shouldReturnPriceWhenValidInput() throws Exception {
        Long productId = 35455L;
        Long brandId = 1L;
        String dateTime = "2020-06-14T10:00:00";

        Price mockPrice = new Price(
                1L,
                brandId,
                LocalDateTime.of(2020, 6, 14, 0,0,0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                productId,
                0,
                BigDecimal.valueOf(35.50),
                Currency.getInstance("EUR")
        );

        PriceResponseDTO mockPriceResponseDTO = new PriceResponseDTO();
        mockPriceResponseDTO.setProductId(35455L);
        mockPriceResponseDTO.setBrandId(1L);
        mockPriceResponseDTO.setStartDate(LocalDateTime.of(2020, 6, 14, 0,0,0));
        mockPriceResponseDTO.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        mockPriceResponseDTO.setPriceList(1);
        mockPriceResponseDTO.setFinalPrice(BigDecimal.valueOf(35.50));
        mockPriceResponseDTO.setCurrency(Currency.getInstance("EUR"));

        when(findPricesUseCase.execute(productId, brandId, LocalDateTime.parse(dateTime)))
                .thenReturn(mockPrice);
        when(priceMapper.toResponseDTO(mockPrice)).thenReturn(mockPriceResponseDTO);

        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("dateTime", dateTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.finalPrice").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void shouldReturnNotFoundWhenPriceNotFound() throws Exception {
        Long productId = 35455L;
        Long brandId = 1L;
        String dateTime = "2020-06-14T10:00:00";

        when(findPricesUseCase.execute(productId, brandId, LocalDateTime.parse(dateTime)))
                .thenReturn(null);

        mockMvc.perform(get("/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("dateTime", dateTime))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(
                        String.format("Price not found for the given productId: %d and brandId: %d", productId, brandId)
                ));
    }

    @Test
    void shouldReturnBadRequestWhenDateFormatIsInvalid() throws Exception {
        long productId = 35455L;
        long brandId = 1L;
        String invalidDateTime = "invalid-date-format";

        mockMvc.perform(get("/prices")
                        .param("productId", Long.toString(productId))
                        .param("brandId", Long.toString(brandId))
                        .param("dateTime", invalidDateTime))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "Invalid date format: " + invalidDateTime
                ));
    }

}