package com.javarush.ikolybaba.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Language {
    private String language;
    private Boolean isOfficial;
    private BigDecimal percentage;
}
