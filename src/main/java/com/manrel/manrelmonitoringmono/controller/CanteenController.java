package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.CanteenRequest;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.response.CanteenResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.service.CanteenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/canteen")
@RequiredArgsConstructor
public class CanteenController {

    private final CanteenService canteenService;

    @PostMapping
    public Response<SaveResponse> create(@RequestBody CanteenRequest canteenRequest) {
        SaveResponse saveResponse = canteenService.saveOrUpdateCanteen(canteenRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<CanteenResponse>> get(@RequestParam String period) {
        List<CanteenResponse> canteenList = canteenService.getCanteenList(period);
        return new Response<>(canteenList);
    }

    @PostMapping("/delete")
    public Response<Void> delete(@RequestBody DeleteRequest request) {
        canteenService.delete(request);
        return new Response<>();
    }
}
