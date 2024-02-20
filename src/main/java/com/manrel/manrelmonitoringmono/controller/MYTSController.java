package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.MytsRequest;
import com.manrel.manrelmonitoringmono.model.response.MytsResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.service.MytsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/myts")
@RequiredArgsConstructor
public class MYTSController {

    private final MytsService mytsService;

    @PostMapping
    public Response<SaveResponse> create(@RequestBody MytsRequest mytsRequest) {
        SaveResponse saveResponse = mytsService.saveOrUpdate(mytsRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<MytsResponse>> get(@RequestParam String period) {
        List<MytsResponse> mytsList = mytsService.getMytsList(period);
        return new Response<>(mytsList);
    }

    @PostMapping("/delete")
    public Response<Void> delete(@RequestBody DeleteRequest request) {
        mytsService.delete(request);
        return new Response<>();
    }
}
