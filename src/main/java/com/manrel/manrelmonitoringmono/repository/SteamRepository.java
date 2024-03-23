package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.Steam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SteamRepository extends JpaRepository<Steam, Long> {

    Steam findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<Steam> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
