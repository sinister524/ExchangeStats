package com.emphasoft.testtusk.TestTuskFromEmphasoft.repository;

import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.Exchange;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo.ExchangeRating;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findAllByAccountId(Long id);

    List<Exchange> findAllByAccountId(Long id, Sort sort);

    @Query("SELECT new com.emphasoft.testtusk.TestTuskFromEmphasoft.pojo.ExchangeRating(c.exchangeCurrency, COUNT(c.exchangeCurrency) AS amount) "
            + "FROM Exchange AS c GROUP BY c.exchangeCurrency ORDER BY amount DESC")
    List<ExchangeRating> getExchangeRating();
}
