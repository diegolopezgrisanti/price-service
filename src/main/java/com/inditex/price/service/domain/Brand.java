package com.inditex.price.service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @Column(name = "brand_id")
    private Long id;
}