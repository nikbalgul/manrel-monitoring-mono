package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.ProcessNaturalGas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessNaturalGas, Long> {

    ProcessNaturalGas findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<ProcessNaturalGas> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}