package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GreaseBoilerResponse {

    private Long id;

    private String measuredDate;

    private Double boiler1;

    private Double boiler1Gnrt;

    private Double boiler2;

    private Double boiler2Gnrt;

    private Double hotOilSteamHour;

    private Double naturalGasSteamHour;

    private Double naturalGasFirstIndex;

    private Double naturalGasLastIndex;

    private Double electricFirstIndex;

    private Double electricLastIndex;

    private Double naturalGasDailyCsmp;

    private Double electricDailyCsmp;
}
