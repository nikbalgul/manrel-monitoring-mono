package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.WaterMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeter, Long> {

    WaterMeter findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<WaterMeter> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
