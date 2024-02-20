package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessResponse {

    private Long id;

    private String measuredDate;

    private Double mechanicCounter;

    private Double mechanicBar;

    private Double mechanicHeat;

    private Double mechanicCsmp;

    private Double digitalV1;

    private Double digitalVB1;

    private Double diffV1Mc;

    private Double digitalBar;

    private Double digitalHeat;

    private Double digitalCsmp;

    private Double radiant;

    private Double radiantCsmp;

    private Double radiantCsmpBar;

    private Double grease;

    private Double greaseCsmp;

    private Double greaseCsmpBar;
}
