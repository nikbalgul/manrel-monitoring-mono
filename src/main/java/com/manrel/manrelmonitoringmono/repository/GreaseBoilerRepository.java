package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.GreaseBoiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GreaseBoilerRepository extends JpaRepository<GreaseBoiler, Long> {

    GreaseBoiler findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<GreaseBoiler> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
