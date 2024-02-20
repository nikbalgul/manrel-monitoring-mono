package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CanteenResponse {

    private Long id;

    private String measuredDate;

    private Double mechanicCounter;

    private Double mechanicBar;

    private Double mechanicHeat;

    private Double digitalV2;

    private Double digitalVB2;

    private Double digitalBar;

    private Double digitalHeat;

    private Double terminalMechanicCounter;

    private Double terminalMechanicBar;

    private Double terminalMechanicHeat;

    private Double kitchenCsmp;

    private Double terminalMechanicCsmp;

    private Double digitalCsmp;

    private Double mechanicCsmp;

    private Double diffV1Mc;
}
