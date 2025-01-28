package com.inditex.price.service.infrastructure.mappers;

import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.infrastructure.entity.PriceEntity;

import java.util.List;

public class PriceMapper {

    private PriceMapper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Price toDomain(PriceEntity entity) {
        Price price = new Price();
        price.setId(entity.getId());
        price.setBrandId(entity.getBrandId());
        price.setStartDate(entity.getStartDate());
        price.setEndDate(entity.getEndDate());
        price.setPriceList(entity.getPriceList());
        price.setProductId(entity.getProductId());
        price.setPriority(entity.getPriority());
        price.setFinalPrice(entity.getFinalPrice());
        price.setCurrency(entity.getCurrency());
        return price;
    }

    public static List<Price> toDomainList(List<PriceEntity> entities) {
        return entities.stream()
                .map(PriceMapper::toDomain)
                .toList();
    }
}