package com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRating {
    private String currencyCharCode;

    private Long count;
}
