package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.WaterMeterRequest;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.WaterMeterResponse;

import java.util.List;

public interface WaterMeterService {

    SaveResponse saveOrUpdate(WaterMeterRequest waterMeterRequest);

    List<WaterMeterResponse> getWaterMeterList(String period);

    void delete(DeleteRequest request);
}
