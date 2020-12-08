package com.emphasoft.testtusk.TestTuskFromEmphasoft.entity;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"account"})
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    private Double value;

    private String currency;

    private Double exchangeValue;

    private String exchangeCurrency;

    private Double inUsd;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Exchange(Double value, String currency, Double exchangeValue, String exchangeCurrency, Double inUsd, Account account) {
        this.timeStamp = new Date();
        this.value = value;
        this.currency = currency;
        this.exchangeValue = exchangeValue;
        this.exchangeCurrency = exchangeCurrency;
        this.inUsd = inUsd;
        this.account = account;
    }

    public String exchangeValueToString() {
        return new DecimalFormat( "###,###.##" ).format(this.exchangeValue);
    }

    public String valueToString() {
        return new DecimalFormat( "###,###.##" ).format(this.value);
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                ", exchangeValue=" + exchangeValue +
                ", exchangeCurrency='" + exchangeCurrency + '\'' +
                ", inUsd=" + inUsd +
                ", account=" + account.getUsername() +
                '}';
    }
}
