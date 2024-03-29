package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class DashboardSteamResponse {

    Map<Integer, Double> steamCsmpMap = new HashMap<>();
    Map<Integer, Double> steamGnrtMap = new HashMap<>();
}
