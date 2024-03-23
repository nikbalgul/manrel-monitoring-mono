package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.SteamRequest;
import com.manrel.manrelmonitoringmono.model.request.YearMonthRequest;
import com.manrel.manrelmonitoringmono.model.response.DashboardSteamResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.SteamResponse;

import java.util.List;

public interface SteamService {

    SaveResponse saveOrUpdate(SteamRequest steamRequest);

    List<SteamResponse> getSteamList(String period);

    void delete(DeleteRequest request);

    DashboardSteamResponse calculate(YearMonthRequest request);
}
