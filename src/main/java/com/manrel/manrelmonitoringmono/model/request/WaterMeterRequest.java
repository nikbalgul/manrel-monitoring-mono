package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WaterMeterRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

    private Double mainCounter;

    private Double mainCsmp;

    private Double boilerCounter;

    private Double boilerCsmp;

    private Double hotWaterCounter;

    private Double hotWaterCsmp;

    private Double other;
}
