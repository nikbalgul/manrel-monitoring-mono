package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MytsResponse {

    private Long id;

    private String measuredDate;

    private Double hour1Value;

    private Double hour2Value;

    private Double hour3Value;

    private Double diffHour2Hour1;

    private Double diffHour3Hour2;

    private Double diffNextHour1Hour3;

    private Double dailySumCsmp;

    private Double dailySumHour;

    private String loadRate;
}
