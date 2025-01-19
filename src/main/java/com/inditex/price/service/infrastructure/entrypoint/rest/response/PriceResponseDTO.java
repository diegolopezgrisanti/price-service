package com.inditex.price.service.infrastructure.entrypoint.rest.response;

import com.inditex.price.service.domain.Brand;
import com.inditex.price.service.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseDTO {

    private Product product;

    private Brand brand;

    private Integer priceList;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal finalPrice;

    private Currency currency;
}