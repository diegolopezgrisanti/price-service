package com.inditex.price.service.infrastructure.mappers;

import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.infrastructure.entity.PriceEntity;
import com.inditex.price.service.infrastructure.entrypoint.rest.response.PriceResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    Price toDomain(PriceEntity entity);
    PriceResponseDTO toResponseDTO(Price price);
}