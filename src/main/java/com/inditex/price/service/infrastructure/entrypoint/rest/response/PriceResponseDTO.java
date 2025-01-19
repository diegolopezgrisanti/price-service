package com.inditex.price.service.infrastructure.entrypoint.rest.response;

import com.inditex.price.service.domain.Brand;
import com.inditex.price.service.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(
            description = "Product ID",
            example = "35455"
    )
    private Product product;

    @Schema(
            description = "Brand ID",
            example = "1"
    )
    private Brand brand;

    @Schema(
            description = "Price list identifier",
            example = "1"
    )
    private Integer priceList;

    @Schema(
            description = "Start date and time of the price",
            example = "2020-06-14T00:00:00"
    )
    private LocalDateTime startDate;

    @Schema(
            description = "End date and time of the price",
            example = "2020-12-31T23:59:59"
    )
    private LocalDateTime endDate;

    @Schema(
            description = "Final price of the product",
            example = "35.50"
    )
    private BigDecimal finalPrice;

    @Schema(
            description = "Price currency",
            example = "EUR"
    )
    private Currency currency;
}