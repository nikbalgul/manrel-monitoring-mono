package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.ProcessRequest;
import com.manrel.manrelmonitoringmono.model.response.ProcessResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;

import java.util.List;

public interface ProcessService {

    SaveResponse saveOrUpdateProcess(ProcessRequest processRequest);

    List<ProcessResponse> getProcessList(String period);

    void delete(DeleteRequest request);
}
