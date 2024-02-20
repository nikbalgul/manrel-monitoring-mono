package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.Myts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MytsRepository extends JpaRepository<Myts, Long> {

    Myts findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<Myts> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
