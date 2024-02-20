package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.GreaseBoilerRequest;
import com.manrel.manrelmonitoringmono.model.response.GreaseBoilerResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface GreaseBoilerService {

    SaveResponse saveOrUpdate(GreaseBoilerRequest greaseBoilerRequest);

    List<GreaseBoilerResponse> getGreaseBoilerList(String period);

    void delete(Long id);
}
