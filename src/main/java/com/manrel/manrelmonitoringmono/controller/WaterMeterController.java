package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.WaterMeterRequest;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.WaterMeterResponse;
import com.manrel.manrelmonitoringmono.service.WaterMeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/water-meter")
@RequiredArgsConstructor
public class WaterMeterController {

    private final WaterMeterService waterMeterService;

    @PostMapping
    public Response<SaveResponse> create(@RequestBody WaterMeterRequest waterMeterRequest) {
        SaveResponse saveResponse = waterMeterService.saveOrUpdate(waterMeterRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<WaterMeterResponse>> get(@RequestParam String period) {
        List<WaterMeterResponse> waterMeterList = waterMeterService.getWaterMeterList(period);
        return new Response<>(waterMeterList);
    }

    @PostMapping("/delete")
    public Response<Void> delete(@RequestBody DeleteRequest request) {
        waterMeterService.delete(request);
        return new Response<>();
    }
}
