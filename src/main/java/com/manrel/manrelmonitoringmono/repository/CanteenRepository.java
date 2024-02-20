package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.CanteenNaturalGas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CanteenRepository extends JpaRepository<CanteenNaturalGas, Long> {

    CanteenNaturalGas findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<CanteenNaturalGas> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
