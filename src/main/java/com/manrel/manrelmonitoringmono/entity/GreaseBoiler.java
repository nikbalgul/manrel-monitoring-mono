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
@Table(name = "GREASE_BOILER_STEAM_CSMP")
@Entity
public class GreaseBoiler implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "BOILER1")
    private Double boiler1;

    @Column(name = "BOILER1_GNRT")
    private Double boiler1Gnrt;

    @Column(name = "BOILER2")
    private Double boiler2;

    @Column(name = "BOILER2_GNRT")
    private Double boiler2Gnrt;

    @Column(name = "HOT_OIL_STEAM_HOUR")
    private Double hotOilSteamHour;

    @Column(name = "NAT_GAS_STEAM_HOUR")
    private Double naturalGasSteamHour;

    @Column(name = "NAT_GAS_FIRST_INDEX")
    private Double naturalGasFirstIndex;

    @Column(name = "NAT_GAS_LAST_INDEX")
    private Double naturalGasLastIndex;

    @Column(name = "ELECTRIC_FIRST_INDEX")
    private Double electricFirstIndex;

    @Column(name = "ELECTRIC_LAST_INDEX")
    private Double electricLastIndex;

    @Column(name = "NAT_GAS_DAILY_CSMP")
    private Double naturalGasDailyCsmp;

    @Column(name = "ELECTRIC_DAILY_CSMP")
    private Double electricDailyCsmp;

    @Embedded
    private Audit audit;
}
