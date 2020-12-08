package com.emphasoft.testtusk.TestTuskFromEmphasoft.service;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.Exchange;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Account;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo.Currency;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo.ExchangeRating;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.repository.ExchangeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@AllArgsConstructor
@Log4j2
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    private final AccountService accountService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Exchange> getAll(Account account) {
        log.info("Load exchange operations list from DataBase...");
        if (account.getRole().getName().contains("ADMIN")) {
            log.debug("For ADMIN getting all exchange operations.");
            return exchangeRepository.findAll(Sort.by(Sort.Direction.DESC,"timeStamp"));
        } else {
            log.debug("For USER, getting only his own exchange operations.");
            return exchangeRepository.findAllByAccountId(account.getId(), Sort.by(Sort.Direction.DESC,"timeStamp"));
        }
    }

    public List<ExchangeRating> getExchangeRating () {
        log.info("Load exchange rating from DataBase...");
        return exchangeRepository.getExchangeRating();
    }

    public Set<Currency> getCurrencies() throws JsonProcessingException {
        log.info("Load exchange rates from https://www.cbr-xml-daily.ru/");
        String response = restTemplate.getForObject("https://www.cbr-xml-daily.ru/daily_json.js",
                String.class);
        assert response != null;
        response = response.substring(237, response.length()-1);
        Map<String, Currency> currencyMap = objectMapper.readValue(response,
                new TypeReference<Map<String,Currency>>(){});
        Set<Currency> currencies = new TreeSet<>(currencyMap.values());
        currencies.add(new Currency("RUB", "Российский рубль", 1.0));
        return currencies;
    }

    public Double exchange(String from, String to, Double value, String username) throws JsonProcessingException {
        Account accountFromDB = accountService.findByLogin(username);
        log.info("Currency conversion for authorized user. Username - " + accountFromDB.getUsername() +
                "; Role - " + accountFromDB.getRole().getName());
        Double exchangeResult = convert(from, to, value);
        Exchange exchange = new Exchange(value, from, exchangeResult, to, convert(from, "USD", value), accountFromDB);
        accountFromDB.addExchange(exchange);
        log.debug("Saving exchange operation to DataBase. " + exchange.toString());
        exchangeRepository.save(exchange);
        accountService.saveAccount(accountFromDB);
        return exchangeResult;
    }
    public Double exchange(String from, String to, Double value) throws JsonProcessingException {
        log.info("Currency conversion for unauthorized user.");
        return convert(from, to, value);
    }

    private Double convert (String from, String to, Double value) throws JsonProcessingException {
        log.info("Conversion " + value + " " + from + " to " + to);
        Set<Currency> currencies = getCurrencies();
        Currency fromCurrency = currencies.stream()
                .filter(currency -> currency.getCharCode().equals(from)).findFirst()
                .orElse(new Currency("RUB", "Российский рубль", 1.0));
        value = value * fromCurrency.getValue();
        Currency toCurrency = currencies.stream()
                .filter(currency -> currency.getCharCode().equals(to)).findFirst()
                .orElse(new Currency("RUB", "Российский рубль", 1.0));
        return value / toCurrency.getValue();
    }
}