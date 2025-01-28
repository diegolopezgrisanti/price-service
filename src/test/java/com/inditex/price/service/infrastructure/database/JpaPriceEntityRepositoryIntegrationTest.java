package com.inditex.price.service.infrastructure.database;

import com.inditex.price.service.domain.models.Price;
import com.inditex.price.service.domain.interfaces.PriceRepository;
import com.inditex.price.service.infrastructure.entity.PriceEntity;
import com.inditex.price.service.infrastructure.mappers.PriceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaPriceEntityRepositoryIntegrationTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private JpaPriceRepository jpaPriceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    private PriceMapper priceMapper;

    Long brandId = 1L;
    Long productId = 35455L;

    @Test
    void shouldFindPricesWithHighestPriority() {
        // GIVEN
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0, 0);

        PriceEntity priceWithLowPriority = new PriceEntity(
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
        PriceEntity priceWithHighPriority = new PriceEntity(
                2L,
                brandId,
                LocalDateTime.of(2020, 6, 14, 15,0,0),
                LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                2,
                productId,
                1,
                BigDecimal.valueOf(25.45),
                Currency.getInstance("EUR")
        );
        givenExistingPrice(priceWithLowPriority);
        givenExistingPrice(priceWithHighPriority);

        // WHEN
        List<PriceEntity> pricesEntity = jpaPriceRepository.findProductPrices(productId, brandId, dateTime);
        List<Price> prices = PriceMapper.toDomainList(pricesEntity);

        // THEN
        assertThat(prices).containsExactly(
                new Price(
                        2L,
                        brandId,
                        LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                        2,
                        productId,
                        1,
                        BigDecimal.valueOf(25.45),
                        Currency.getInstance("EUR")),
                new Price(
                        1L,
                        brandId,
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        1,
                        productId,
                        0,
                        BigDecimal.valueOf(35.50),
                        Currency.getInstance("EUR"))
        );
    }

    @Test
    void shouldReturnEmptyListWhenNoPricesMatch() {
        // GIVEN
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 19, 16, 0, 0);

        PriceEntity priceEntity = new PriceEntity(
                3L,
                brandId,
                LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                3,
                productId,
                1,
                BigDecimal.valueOf(30.50),
                Currency.getInstance("EUR")
        );
        givenExistingPrice(priceEntity);

        // WHEN
        List<PriceEntity> pricesEntity = jpaPriceRepository.findProductPrices(productId, brandId, dateTime);
        List<Price> prices = PriceMapper.toDomainList(pricesEntity);

        // THEN
        assertThat(prices).isEmpty();
    }

    private void givenExistingPrice(PriceEntity priceEntity) {
        entityManager.merge(priceEntity);
        entityManager.flush();
    }

}