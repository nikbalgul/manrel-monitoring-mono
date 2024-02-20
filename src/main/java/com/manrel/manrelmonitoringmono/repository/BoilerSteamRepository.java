package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.BoilerSteam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BoilerSteamRepository extends JpaRepository<BoilerSteam, Long> {

    BoilerSteam findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<BoilerSteam> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
