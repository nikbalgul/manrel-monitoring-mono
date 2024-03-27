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
@Table(name = "STEAM_CSMP")
@Entity
public class Steam implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "MINERAL_OIL")
    private Double mineralOil;

    @Column(name = "MINERAL_OIL_CSMP")
    private Double mineralOilCsmp;

    @Column(name = "ESANJOR")
    private Double esanjor;

    @Column(name = "ESANJOR_CSMP")
    private Double esanjorCsmp;

    @Column(name = "OLD_WHARF")
    private Double oldWharf;

    @Column(name = "OLD_WHARF_CSMP")
    private Double oldWharfCsmp;

    @Column(name = "NEW_WHARF")
    private Double newWharf;

    @Column(name = "NEW_WHARF_CSMP")
    private Double newWharfCsmp;

    @Column(name = "DN40")
    private Double dn40;

    @Column(name = "DN40_CSMP")
    private Double dn40Csmp;

    @Column(name = "DN150")
    private Double dn150;

    @Column(name = "DN150_CSMP")
    private Double dn150Csmp;

    @Column(name = "SUM_DN40_DN_150")
    private Double sumDn40Dn150;

    @Column(name = "SUM_CSMP_DN40_DN_150")
    private Double sumCsmpDn40Dn150;

    @Column(name = "SUM_CSMP")
    private Double sumCsmp;

    @Embedded
    private Audit audit;
}
