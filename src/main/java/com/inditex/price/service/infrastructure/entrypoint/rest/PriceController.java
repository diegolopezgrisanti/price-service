package com.inditex.price.service.infrastructure.entrypoint.rest;

import com.inditex.price.service.application.findprices.FindPricesUseCaseImpl;
import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.domain.exception.InvalidDateFormatException;
import com.inditex.price.service.domain.exception.PriceNotFoundException;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.PriceResponseDTO;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.error.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/prices")
@Tag(
        name = "Price API",
        description = "API for retrieving and managing product prices based on product ID, brand, and date/time criteria."
)
@RequiredArgsConstructor
public class PriceController {

    private final FindPricesUseCaseImpl findPricesUseCaseImpl;

    @Operation(
            summary = "Get the price for a product",
            description = "Retrieve the price of a specific product for a given brand and timestamp."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Price retrieved successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    }
            )
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PriceResponseDTO> getPrices(
            @Parameter(
                    description = "Product ID to fetch the price for",
                    example = "35455"
            )
            @RequestParam Long productId,

            @Parameter(
                    description = "Brand ID associated with the product",
                    example = "1"
            )
            @RequestParam Long brandId,

            @Parameter(
                    description = "Date and time for which to fetch the price",
                    example = "2020-06-14T10:00:00"
            )
            @RequestParam String dateTime
    ) {

        LocalDateTime parsedDateTime = parseDateTime(dateTime);

        Price price = findPricesUseCaseImpl.execute(productId, brandId, parsedDateTime);

        if (price == null) {
            String errorMessage = String.format(
                    "Price not found for the given productId: %d and brandId: %d",
                    productId,
                    brandId
            );
            throw new PriceNotFoundException(errorMessage);
        }

        PriceResponseDTO priceResponseDTO = new PriceResponseDTO(
                price.getProductId(),
                price.getBrandId(),
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