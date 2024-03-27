package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class DashboardTtsResponse {

    Map<Integer, Double> ttsMap = new HashMap<>();
}
