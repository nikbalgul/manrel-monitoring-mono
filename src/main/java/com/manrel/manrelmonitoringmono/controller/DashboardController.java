package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.response.DashboardResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public Response<DashboardResponse> get() {
        DashboardResponse dashboardResponse = dashboardService.get();
        return new Response<>(dashboardResponse);
    }
}
