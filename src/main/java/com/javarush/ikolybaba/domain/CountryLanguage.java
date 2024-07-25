package com.javarush.ikolybaba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;

@Entity
@Table(schema = "world", name = "country_language")
@Getter
@Setter
@ToString
public class CountryLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    private Country country;

    private String language;

    @Column(name = "is_official", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isOfficial;

    private BigDecimal percentage;
}
