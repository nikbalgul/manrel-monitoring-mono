package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.ChemicalSaltRequest;
import com.manrel.manrelmonitoringmono.model.response.ChemicalSaltResponse;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.service.ChemicalSaltService;
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
@RequestMapping("/api/v1/chemical-salt")
@RequiredArgsConstructor
public class ChemicalSaltController {

    private final ChemicalSaltService chemicalSaltService;

    @PostMapping
    public Response<SaveResponse> create(@RequestBody ChemicalSaltRequest chemicalSaltRequest) {
        SaveResponse saveResponse = chemicalSaltService.saveOrUpdate(chemicalSaltRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<ChemicalSaltResponse>> get(@RequestParam String period) {
        List<ChemicalSaltResponse> chemicalSaltList = chemicalSaltService.getChemicalSaltList(period);
        return new Response<>(chemicalSaltList);
    }

    @DeleteMapping
    public Response<Void> delete(@RequestParam Long id) {
        chemicalSaltService.delete(id);
        return new Response<>();
    }
}
