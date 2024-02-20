package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoilerSteamResponse {

    private Long id;

    private String measuredDate;

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
