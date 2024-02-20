package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChemicalSaltRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

    private Double stock4003;

    private Double received4003;

    private Double used4003;

    private Double stock4007;

    private Double received4007;

    private Double used4007;

    private Double stock4010;

    private Double received4010;

    private Double used4010;

    private Double saltStock;

    private Double saltReceived;

    private Double saltUsed;
}
