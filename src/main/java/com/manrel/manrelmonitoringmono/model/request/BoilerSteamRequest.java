package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BoilerSteamRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

    private Double boiler1;

    private Double gnrt1;

    private Double boiler2;

    private Double gnrt2;

    private Double erensanBoiler1;

    private Double erensanGnrt1;

    private Double erensanBoiler2;

    private Double erensanGnrt2;

    private Double sumGnrt;
}
