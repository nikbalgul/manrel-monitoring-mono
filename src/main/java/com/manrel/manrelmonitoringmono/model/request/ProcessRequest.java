package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProcessRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

    private Double mechanicCounter;

    private Double mechanicBar;

    private Double mechanicHeat;

    private Double digitalV1;

    private Double digitalVB1;

    private Double digitalBar;

    private Double digitalHeat;

    private Double radiant;

    private Double grease;
}
