package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.GreaseChemicalSaltRequest;
import com.manrel.manrelmonitoringmono.model.response.GreaseChemicalSaltResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface GreaseChemicalSaltService {

    SaveResponse saveOrUpdate(GreaseChemicalSaltRequest greaseChemicalSaltRequest);

    List<GreaseChemicalSaltResponse> getGreaseChemicalSaltList(String period);

    void delete(Long id);
}
