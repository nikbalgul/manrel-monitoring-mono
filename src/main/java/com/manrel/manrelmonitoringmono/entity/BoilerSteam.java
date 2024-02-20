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
@Table(name = "BOILER_STEAM_GNRT")
@Entity
public class BoilerSteam implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "BOILER1")
    private Double boiler1;

    @Column(name = "GNRT1")
    private Double gnrt1;

    @Column(name = "BOILER2")
    private Double boiler2;

    @Column(name = "GNRT2")
    private Double gnrt2;

    @Column(name = "ERENSAN_BOILER1")
    private Double erensanBoiler1;

    @Column(name = "ERENSAN_GNRT1")
    private Double erensanGnrt1;

    @Column(name = "ERENSAN_BOILER2")
    private Double erensanBoiler2;

    @Column(name = "ERENSAN_GNRT2")
    private Double erensanGnrt2;

    @Column(name = "SUM_GNRT")
    private Double sumGnrt;

    @Embedded
    private Audit audit;
}
