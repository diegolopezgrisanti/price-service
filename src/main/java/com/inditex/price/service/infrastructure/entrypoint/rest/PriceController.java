package com.inditex.price.service.infrastructure.entrypoint.rest;

import com.inditex.price.service.application.findprices.FindPricesUseCase;
import com.inditex.price.service.domain.Price;
import com.inditex.price.service.domain.exception.InvalidDateFormatException;
import com.inditex.price.service.domain.exception.PriceNotFoundException;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.PriceResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final FindPricesUseCase findPricesUseCase;

    public PriceController(FindPricesUseCase findPricesUseCase) {
        this.findPricesUseCase = findPricesUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PriceResponseDTO> getPrices(
            @RequestParam Long productId,
            @RequestParam Long brandId,
            @RequestParam String dateTime
    ) {

        LocalDateTime parsedDateTime = parseDateTime(dateTime);

        Price price = findPricesUseCase.execute(productId, brandId, parsedDateTime);

        if (price == null) {
            String errorMessage = String.format(
                    "Price not found for the given productId: %d and brandId: %d",
                    productId,
                    brandId
            );
            throw new PriceNotFoundException(errorMessage);
        }

        PriceResponseDTO priceResponseDTO = new PriceResponseDTO(
                price.getProduct(),
                price.getBrand(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getFinalPrice(),
                price.getCurrency()
        );

        return ResponseEntity.ok(priceResponseDTO);

    }

    private LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format: " + dateTime);
        }
    }
}