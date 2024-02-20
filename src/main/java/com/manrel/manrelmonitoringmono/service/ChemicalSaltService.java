package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.ChemicalSaltRequest;
import com.manrel.manrelmonitoringmono.model.response.ChemicalSaltResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface ChemicalSaltService {

    SaveResponse saveOrUpdate(ChemicalSaltRequest chemicalSaltRequest);

    List<ChemicalSaltResponse> getChemicalSaltList(String period);

    void delete(Long id);
}
