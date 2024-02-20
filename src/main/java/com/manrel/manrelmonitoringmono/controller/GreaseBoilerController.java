package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.GreaseBoilerRequest;
import com.manrel.manrelmonitoringmono.model.response.GreaseBoilerResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.service.GreaseBoilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grease-boiler")
@RequiredArgsConstructor
public class GreaseBoilerController {

    private final GreaseBoilerService greaseBoilerService;

    @PostMapping
    public Response<SaveResponse> create(@RequestBody GreaseBoilerRequest greaseBoilerRequest) {
        SaveResponse saveResponse = greaseBoilerService.saveOrUpdate(greaseBoilerRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<GreaseBoilerResponse>> get(@RequestParam String period) {
        List<GreaseBoilerResponse> greaseBoilerList = greaseBoilerService.getGreaseBoilerList(period);
        return new Response<>(greaseBoilerList);
    }

    @DeleteMapping
    public Response<Void> delete(@RequestParam Long id) {
        greaseBoilerService.delete(id);
        return new Response<>();
    }
}
