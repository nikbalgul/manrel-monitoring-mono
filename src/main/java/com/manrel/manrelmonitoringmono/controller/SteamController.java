package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.SteamRequest;
import com.manrel.manrelmonitoringmono.model.request.YearMonthRequest;
import com.manrel.manrelmonitoringmono.model.response.DashboardSteamCsmpResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.SteamResponse;
import com.manrel.manrelmonitoringmono.service.SteamService;
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
@RequestMapping("/api/v1/steam")
@RequiredArgsConstructor
public class SteamController {

    private final SteamService steamService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANREL_USER')")
    public Response<SaveResponse> create(@RequestBody SteamRequest steamRequest) {
        SaveResponse saveResponse = steamService.saveOrUpdate(steamRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<SteamResponse>> get(@RequestParam String period) {
        List<SteamResponse> steamList = steamService.getSteamList(period);
        return new Response<>(steamList);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN','MANREL_USER')")
    public Response<Void> delete(@RequestBody DeleteRequest request) {
        steamService.delete(request);
        return new Response<>();
    }

    @GetMapping("/calculate")
    public Response<DashboardSteamCsmpResponse> calculate(@ModelAttribute YearMonthRequest request) {
        DashboardSteamCsmpResponse steamResponse = steamService.calculate(request);
        return new Response<>(steamResponse);
    }
}
