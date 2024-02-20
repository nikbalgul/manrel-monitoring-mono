package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.GreaseChemicalSalt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GreaseChemicalSaltRepository extends JpaRepository<GreaseChemicalSalt, Long> {

    GreaseChemicalSalt findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<GreaseChemicalSalt> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
