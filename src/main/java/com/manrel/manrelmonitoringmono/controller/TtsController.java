package com.manrel.manrelmonitoringmono.controller;

import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.TtsRequest;
import com.manrel.manrelmonitoringmono.model.response.Response;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.TtsResponse;
import com.manrel.manrelmonitoringmono.service.TtsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class TtsController {

    private final TtsService ttsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANREL_USER')")
    public Response<SaveResponse> create(@RequestBody TtsRequest ttsRequest) {
        SaveResponse saveResponse = ttsService.saveOrUpdate(ttsRequest);
        return new Response<>(saveResponse);
    }

    @GetMapping("/list")
    public Response<List<TtsResponse>> get(@RequestParam String period) {
        List<TtsResponse> ttsList = ttsService.getTtsList(period);
        return new Response<>(ttsList);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN','MANREL_USER')")
    public Response<Void> delete(@RequestBody DeleteRequest request) {
        ttsService.delete(request);
        return new Response<>();
    }
}
