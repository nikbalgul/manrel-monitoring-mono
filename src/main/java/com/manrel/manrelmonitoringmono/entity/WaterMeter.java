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
@Table(name = "WATER_METER_CSMP")
@Entity
public class WaterMeter implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "MAIN_COUNTER")
    private Double mainCounter;

    /*
     * Sonraki gunun değerinden gunun değerinin farki
     * */
    @Column(name = "MAIN_CSMP")
    private Double mainCsmp;

    @Column(name = "BOILER_COUNTER")
    private Double boilerCounter;

    /*
     * Sonraki gunun değerinden gunun değerinin farki
     * */
    @Column(name = "BOILER_CSMP")
    private Double boilerCsmp;

    @Column(name = "HOT_WATER_COUNTER")
    private Double hotWaterCounter;

    /*
     * Sonraki gunun değerinden gunun değerinin farki
     * */
    @Column(name = "HOT_WATER_CSMP")
    private Double hotWaterCsmp;

    /*
     * Main counter - (Boiler Counter + Hot Water Counter)
     * */
    @Column(name = "OTHER")
    private Double other;

    @Embedded
    private Audit audit;
}
