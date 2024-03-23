package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.ProcessRequest;
import com.manrel.manrelmonitoringmono.model.request.YearMonthRequest;
import com.manrel.manrelmonitoringmono.model.response.DashboardSteamResponse;
import com.manrel.manrelmonitoringmono.model.response.ProcessResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANREL_USER')")
    public Response<SaveResponse> create(@RequestBody ProcessRequest processRequest) {
        SaveResponse saveResponse = processService.saveOrUpdateProcess(processRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<ProcessResponse>> get(@RequestParam String period) {
        List<ProcessResponse> processList = processService.getProcessList(period);
        return new Response<>(processList);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN','MANREL_USER')")
    public Response<Void> delete(@RequestBody DeleteRequest processDeleteRequest) {
        processService.delete(processDeleteRequest);
        return new Response<>();
    }
}
