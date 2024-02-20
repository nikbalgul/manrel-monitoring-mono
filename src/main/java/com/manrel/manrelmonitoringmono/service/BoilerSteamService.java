package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.BoilerSteamRequest;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.response.BoilerSteamResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface BoilerSteamService {

    SaveResponse saveOrUpdate(BoilerSteamRequest boilerSteamRequest);

    List<BoilerSteamResponse> getBoilerSteamList(String period);

    void delete(DeleteRequest request);
}
