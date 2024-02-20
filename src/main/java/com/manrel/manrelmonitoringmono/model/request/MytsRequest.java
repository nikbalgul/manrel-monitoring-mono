package com.manrel.manrelmonitoringmono.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MytsRequest {

    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date measuredDate;

    private Double hour1Value;

    private Double hour2Value;

    private Double hour3Value;
}
