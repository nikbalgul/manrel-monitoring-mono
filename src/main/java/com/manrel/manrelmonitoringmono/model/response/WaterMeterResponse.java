package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaterMeterResponse {

    private Long id;

    private String measuredDate;

    private Double mainCounter;

    private Double mainCsmp;

    private Double boilerCounter;

    private Double boilerCsmp;

    private Double hotWaterCounter;

    private Double hotWaterCsmp;

    private Double other;
}
