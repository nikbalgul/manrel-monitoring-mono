package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class DashboardProcessResponse {

    Map<Integer, Double> mechanicMap = new HashMap<>();
    Map<Integer, Double> digitalMap = new HashMap<>();
    Map<Integer, Double> greaseMap = new HashMap<>();
}
