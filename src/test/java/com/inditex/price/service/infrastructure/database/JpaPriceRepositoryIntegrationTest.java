package com.inditex.price.service.infrastructure.database;

import com.inditex.price.service.domain.Brand;
import com.inditex.price.service.domain.Price;
import com.inditex.price.service.domain.PriceRepository;
import com.inditex.price.service.domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaPriceRepositoryIntegrationTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private JpaPriceRepository jpaPriceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Long brandId = 1L;
    Long productId = 35455L;

    @Test
    void shouldFindPricesOrderedByPriority() {
        // GIVEN
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        Brand brand = entityManager.find(Brand.class, brandId);
        Product product = entityManager.find(Product.class, productId);

        Price priceWithLowPriority = new Price(
                1L,
                brand,
                LocalDateTime.of(2020, 6, 14, 0,0,0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                product,
                0,
                BigDecimal.valueOf(35.50),
                Currency.getInstance("EUR")
        );
        Price priceWithHighPriority = new Price(
                2L,
                brand,
                LocalDateTime.of(2020, 6, 14, 15,0,0),
                LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                2,
                product,
                1,
                BigDecimal.valueOf(25.45),
                Currency.getInstance("EUR")
        );
        givenExistingPrice(priceWithLowPriority);
        givenExistingPrice(priceWithHighPriority);

        // WHEN
        List<Price> prices = jpaPriceRepository.findProductPrices(productId, brandId, dateTime);

        // THEN
        assertThat(prices).containsExactly(priceWithHighPriority, priceWithLowPriority);
    }

    @Test
    void shouldReturnEmptyListWhenNoPricesMatch() {
        // GIVEN
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 19, 16, 0, 0);
        Brand brand = entityManager.find(Brand.class, brandId);
        Product product = entityManager.find(Product.class, productId);

        Price price = new Price(
                1L,
                brand,
                LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                3,
                product,
                1,
                BigDecimal.valueOf(30.50),
                Currency.getInstance("EUR")
        );
        givenExistingPrice(price);

        // WHEN
        List<Price> prices = jpaPriceRepository.findProductPrices(productId, brandId, dateTime);

        // THEN
        assertThat(prices).isEmpty();
    }

    private void givenExistingPrice(Price price) {
        entityManager.merge(price.getBrand());
        entityManager.merge(price.getProduct());
        entityManager.merge(price);
        entityManager.flush();
    }

}