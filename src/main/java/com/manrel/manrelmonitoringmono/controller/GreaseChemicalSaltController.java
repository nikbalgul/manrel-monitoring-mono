package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.GreaseChemicalSaltRequest;
import com.manrel.manrelmonitoringmono.model.response.GreaseChemicalSaltResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.service.GreaseChemicalSaltService;
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
@RequestMapping("/api/v1/grease-chem-salt")
@RequiredArgsConstructor
public class GreaseChemicalSaltController {

    private final GreaseChemicalSaltService greaseChemicalSaltService;

    @PostMapping
    public Response<SaveResponse> create(@RequestBody GreaseChemicalSaltRequest greaseChemicalSaltRequest) {
        SaveResponse saveResponse = greaseChemicalSaltService.saveOrUpdate(greaseChemicalSaltRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<GreaseChemicalSaltResponse>> get(@RequestParam String period) {
        List<GreaseChemicalSaltResponse> greaseChemicalSaltList = greaseChemicalSaltService.getGreaseChemicalSaltList(period);
        return new Response<>(greaseChemicalSaltList);
    }

    @DeleteMapping
    public Response<Void> delete(@RequestParam Long id) {
        greaseChemicalSaltService.delete(id);
        return new Response<>();
    }
}
