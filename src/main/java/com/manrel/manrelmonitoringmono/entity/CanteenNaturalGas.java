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
@Table(name = "CANTEEN_NG_CSMP")
@Entity
public class CanteenNaturalGas implements Serializable, Auditable {

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
    private Double digitalV2;

    @Column(name = "DIFF_V1_MC")
    private Double diffV1Mc;

    @Column(name = "DIGITAL_VB1")
    private Double digitalVB2;

    @Column(name = "DIGITAL_CSMP")
    private Double digitalCsmp;

    @Column(name = "DIGITAL_BAR")
    private Double digitalBar;

    @Column(name = "DIGITAL_HEAT")
    private Double digitalHeat;

    @Column(name = "TERMINAL_MECHANIC_COUNTER")
    private Double terminalMechanicCounter;

    @Column(name = "TERMINAL_MECHANIC_CSMP")
    private Double terminalMechanicCsmp;

    @Column(name = "TERMINAL_MECHANIC_BAR")
    private Double terminalMechanicBar;

    @Column(name = "TERMINAL_MECHANIC_HEAT")
    private Double terminalMechanicHeat;

    @Column(name = "KITCHEN_CSMP")
    private Double kitchenCsmp;

    @Embedded
    private Audit audit;
}
