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
@Table(name = "CHEMICAL_SALT_STOCK")
@Entity
public class ChemicalSalt implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEASURED_DATE")
    private Date measuredDate;

    @Column(name = "STOCK_4003")
    private Double stock4003;

    @Column(name = "RECEIVED_4003")
    private Double received4003;

    @Column(name = "USED_4003")
    private Double used4003;

    @Column(name = "STOCK_4007")
    private Double stock4007;

    @Column(name = "RECEIVED_4007")
    private Double received4007;

    @Column(name = "USED_4007")
    private Double used4007;

    @Column(name = "STOCK_4010")
    private Double stock4010;

    @Column(name = "RECEIVED_4010")
    private Double received4010;

    @Column(name = "USED_4010")
    private Double used4010;

    @Column(name = "SALT_STOCK")
    private Double saltStock;

    @Column(name = "SALT_RECEIVED")
    private Double saltReceived;

    @Column(name = "SALT_USED")
    private Double saltUsed;

    @Embedded
    private Audit audit;
}
