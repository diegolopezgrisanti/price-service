package com.inditex.price.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "price_list", nullable = false)
    private Integer priceList;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "price", nullable = false)
    private BigDecimal finalPrice;

    @Column(name = "curr", nullable = false)
    private Currency currency;
}