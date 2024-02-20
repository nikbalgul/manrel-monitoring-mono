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
@Table(name = "PROCESS_NG_CSMP")
@Entity
public class ProcessNaturalGas implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "MECHANIC_COUNTER")
    private Double mechanicCounter;

    @Column(name = "MECHANIC_CSMP")
    private Double mechanicCsmp;

    @Column(name = "MECHANIC_BAR")
    private Double mechanicBar;

    @Column(name = "MECHANIC_HEAT")
    private Double mechanicHeat;

    @Column(name = "DIGITAL_V1")
    private Double digitalV1;

    @Column(name = "DIFF_V1_MC")
    private Double diffV1Mc;

    @Column(name = "DIGITAL_VB1")
    private Double digitalVB1;

    @Column(name = "DIGITAL_CSMP")
    private Double digitalCsmp;

    @Column(name = "DIGITAL_BAR")
    private Double digitalBar;

    @Column(name = "DIGITAL_HEAT")
    private Double digitalHeat;

    @Column(name = "RADIANT")
    private Double radiant;

    @Column(name = "RADIANT_CSMP")
    private Double radiantCsmp;

    @Column(name = "RADIANT_CSMP_BAR")
    private Double radiantCsmpBar;

    @Column(name = "GREASE")
    private Double grease;

    @Column(name = "GREASE_CSMP")
    private Double greaseCsmp;

    @Column(name = "GREASE_CSMP_BAR")
    private Double greaseCsmpBar;

    @Embedded
    private Audit audit;
}
