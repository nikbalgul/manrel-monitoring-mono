package com.manrel.manrelmonitoringmono.entity;

import com.manrel.manrelmonitoringmono.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Table(name = "TTS_STEAM_CSMP")
@Entity
public class Tts implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "HOUR1_VALUE")
    private Double hour1Value;

    @Column(name = "HOUR2_VALUE")
    private Double hour2Value;

    @Column(name = "HOUR3_VALUE")
    private Double hour3Value;

    @Column(name = "DIFF_H2_H1")
    private Double diffHour2Hour1;

    @Column(name = "DIFF_H3_H2")
    private Double diffHour3Hour2;

    /*
     * Sonraki gunun ilk kaydindan gunun son kaydinin farki
     * */
    @Column(name = "DIFF_NH1_H3")
    private Double diffNextHour1Hour3;

    /*
     * Sonraki gunun ilk kaydindan gunun ilk kaydinin farki, gunluk tuketim
     * */
    @Column(name = "DAILY_SUM_CSMP")
    private Double dailySumCsmp;

    /*
     * Saatlik tuketim (CSMP/24)
     * */
    @Column(name = "DAILY_SUM_HOUR")
    private Double dailySumHour;

    /*
     * Saatlik Tuketim / 6700
     * */
    @Column(name = "LOAD_RATE")
    private Double loadRate;

    @Embedded
    private Audit audit;
}
