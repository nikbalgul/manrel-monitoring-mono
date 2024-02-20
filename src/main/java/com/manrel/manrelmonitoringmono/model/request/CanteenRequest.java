package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CanteenRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

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
}
