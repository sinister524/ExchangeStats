package com.emphasoft.testtusk.TestTuskFromEmphasoft.service;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo.Currency;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.TreeSet;

class ExchangeServiceTest {

    ExchangeService service;

    @Test
    void convert() throws JsonProcessingException {
        double result = service.exchange("USD", "EUR", 573.29);
        System.out.println(result);
    }

    @Test
    void getCurrencies() throws JsonProcessingException {
        Set<Currency> currencies = service.getCurrencies();
        Assert.isInstanceOf(TreeSet.class, currencies);
        System.out.println(currencies);
    }

}