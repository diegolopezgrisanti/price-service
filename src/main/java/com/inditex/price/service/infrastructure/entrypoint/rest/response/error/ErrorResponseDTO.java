package com.inditex.price.service.infrastructure.entrypoint.rest.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    @Schema(
            description = "Error message describing the issue",
            example = "Product not found"
    )
    private String message;
}