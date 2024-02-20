package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.CanteenRequest;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.response.CanteenResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface CanteenService {

    SaveResponse saveOrUpdateCanteen(CanteenRequest canteenRequest);

    List<CanteenResponse> getCanteenList(String period);

    void delete(DeleteRequest request);
}
