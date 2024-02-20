package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.MytsRequest;
import com.manrel.manrelmonitoringmono.model.response.MytsResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface MytsService {

    SaveResponse saveOrUpdate(MytsRequest mytsRequest);

    List<MytsResponse> getMytsList(String period);

    void delete(DeleteRequest request);
}
