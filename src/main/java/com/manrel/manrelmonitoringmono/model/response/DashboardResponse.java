package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardResponse {

    private DashboardProcessResponse dashboardProcess;

    private DashboardSteamCsmpResponse dashboardSteamCsmp;

    private DashboardTtsResponse dashboardTts;

    private DashboardSteamResponse dashboardSteam;
}
