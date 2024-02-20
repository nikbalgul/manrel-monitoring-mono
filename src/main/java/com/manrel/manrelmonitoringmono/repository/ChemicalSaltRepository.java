package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.ChemicalSalt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChemicalSaltRepository extends JpaRepository<ChemicalSalt, Long> {

    ChemicalSalt findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<ChemicalSalt> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
