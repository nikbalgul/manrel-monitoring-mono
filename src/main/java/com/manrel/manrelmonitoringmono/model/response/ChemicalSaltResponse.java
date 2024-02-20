package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChemicalSaltResponse {

    private Long id;

    private String measuredDate;

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
