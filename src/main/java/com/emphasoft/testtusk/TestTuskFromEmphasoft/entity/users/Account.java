package com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.Exchange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String firstName;

    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Exchange> exchanges;

    public Account(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Double getMaxExchange() {
        return exchanges.stream()
                .mapToDouble(Exchange::getInUsd)
                .max()
                .orElse(0.0);
    }

    public Double getTotalExchange() {
        return exchanges.stream()
                .mapToDouble(Exchange::getInUsd)
                .sum();
    }

    public void addExchange(Exchange exchange) {
        this.exchanges.add(exchange);
    }

    public String maxExchangeToString() {
        return new DecimalFormat( "###,###.##" ).format(getMaxExchange());
    }

    public String totalExchangeToString() {
        return new DecimalFormat( "###,###.##" ).format(getTotalExchange());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role.getName() +
                '}';
    }
}
