package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GreaseBoilerRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

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
}
