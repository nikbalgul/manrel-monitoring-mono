package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.TtsRequest;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.TtsResponse;

import java.util.List;

public interface TtsService {

    SaveResponse saveOrUpdate(TtsRequest ttsRequest);

    List<TtsResponse> getTtsList(String period);

    void delete(DeleteRequest request);
}
