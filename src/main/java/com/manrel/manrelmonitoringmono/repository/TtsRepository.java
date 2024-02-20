package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.Tts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TtsRepository extends JpaRepository<Tts, Long> {

    Tts findByMeasuredDate(Date measuredDate);

    boolean existsByMeasuredDate(Date measuredDate);

    List<Tts> findByMeasuredDateBetweenOrderByMeasuredDateDesc(Date periodDate, Date now);
}
