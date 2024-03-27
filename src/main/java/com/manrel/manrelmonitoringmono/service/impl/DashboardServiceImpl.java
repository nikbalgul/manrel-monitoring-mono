package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.BoilerSteam;
import com.manrel.manrelmonitoringmono.entity.ProcessNaturalGas;
import com.manrel.manrelmonitoringmono.entity.Steam;
import com.manrel.manrelmonitoringmono.entity.Tts;
import com.manrel.manrelmonitoringmono.model.response.DashboardProcessResponse;
import com.manrel.manrelmonitoringmono.model.response.DashboardResponse;
import com.manrel.manrelmonitoringmono.model.response.DashboardSteamCsmpResponse;
import com.manrel.manrelmonitoringmono.model.response.DashboardSteamResponse;
import com.manrel.manrelmonitoringmono.model.response.DashboardTtsResponse;
import com.manrel.manrelmonitoringmono.repository.BoilerSteamRepository;
import com.manrel.manrelmonitoringmono.repository.ProcessRepository;
import com.manrel.manrelmonitoringmono.repository.SteamRepository;
import com.manrel.manrelmonitoringmono.repository.TtsRepository;
import com.manrel.manrelmonitoringmono.service.DashboardService;
import com.manrel.manrelmonitoringmono.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ProcessRepository processRepository;

    private final SteamRepository steamRepository;

    private final TtsRepository ttsRepository;

    private final BoilerSteamRepository boilerSteamRepository;

    @Override
    public DashboardResponse get() {

        DashboardResponse dashboardResponse = new DashboardResponse();

        dashboardResponse.setDashboardSteamCsmp(fillSteamCsmpResponse());

        dashboardResponse.setDashboardProcess(fillProcessResponse());
        
        dashboardResponse.setDashboardTts(fillTtsResponse());

        dashboardResponse.setDashboardSteam(fillSteamResponse());

        return dashboardResponse;
    }

    private DashboardSteamResponse fillSteamResponse() {
        Calendar firstDayOfYear = Calendar.getInstance();
        Calendar lastDayOfYear = Calendar.getInstance();
        firstDayOfYear.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfYear.set(Calendar.MONTH, 0);
        DateUtils.setZeroTime(firstDayOfYear);
        lastDayOfYear.set(Calendar.MONTH, 11);
        lastDayOfYear.set(Calendar.DAY_OF_MONTH, lastDayOfYear.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateUtils.setZeroTime(lastDayOfYear);

        List<Steam> steamListOfYear = steamRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(firstDayOfYear.getTime(), lastDayOfYear.getTime());
        List<BoilerSteam> boilerSteamListOfYear = boilerSteamRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(firstDayOfYear.getTime(), lastDayOfYear.getTime());

        Map<Integer, Double> steamMap = steamListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getSumCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(Steam::getSumCsmp)));

        Map<Integer, Double> boilerSteamMap = boilerSteamListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getSumGnrt()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(BoilerSteam::getSumGnrt)));

        DashboardSteamResponse dashboardSteamResponse = new DashboardSteamResponse();
        dashboardSteamResponse.setSteamCsmpMap(steamMap);
        dashboardSteamResponse.setSteamGnrtMap(boilerSteamMap);
        return dashboardSteamResponse;
    }

    private DashboardTtsResponse fillTtsResponse() {
        Calendar firstDayOfYear = Calendar.getInstance();
        Calendar lastDayOfYear = Calendar.getInstance();
        firstDayOfYear.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfYear.set(Calendar.MONTH, 0);
        DateUtils.setZeroTime(firstDayOfYear);
        lastDayOfYear.set(Calendar.MONTH, 11);
        lastDayOfYear.set(Calendar.DAY_OF_MONTH, lastDayOfYear.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateUtils.setZeroTime(lastDayOfYear);
        List<Tts> ttsListOfYear = ttsRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(firstDayOfYear.getTime(), lastDayOfYear.getTime());

        Map<Integer, Double> ttsMap = ttsListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getDailySumCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(Tts::getDailySumCsmp)));

        DashboardTtsResponse dashboardTtsResponse = new DashboardTtsResponse();
        dashboardTtsResponse.setTtsMap(ttsMap);
        return dashboardTtsResponse;
    }

    private DashboardProcessResponse fillProcessResponse() {

        Calendar firstDayOfYear = Calendar.getInstance();
        Calendar lastDayOfYear = Calendar.getInstance();
        firstDayOfYear.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfYear.set(Calendar.MONTH, 0);
        DateUtils.setZeroTime(firstDayOfYear);
        lastDayOfYear.set(Calendar.MONTH, 11);
        lastDayOfYear.set(Calendar.DAY_OF_MONTH, lastDayOfYear.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateUtils.setZeroTime(lastDayOfYear);
        List<ProcessNaturalGas> processListOfYear = processRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(firstDayOfYear.getTime(), lastDayOfYear.getTime());

        Map<Integer, Double> digitalMap = processListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getDigitalCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(ProcessNaturalGas::getDigitalCsmp)));

        Map<Integer, Double> radiantMap = processListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getRadiantCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(ProcessNaturalGas::getRadiantCsmp)));

        Map<Integer, Double> greaseMap = processListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getGreaseCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(ProcessNaturalGas::getGreaseCsmp)));

        DashboardProcessResponse dashboardProcessResponse = new DashboardProcessResponse();
        dashboardProcessResponse.setDigitalMap(digitalMap);
        dashboardProcessResponse.setRadiantMap(radiantMap);
        dashboardProcessResponse.setGreaseMap(greaseMap);
        return dashboardProcessResponse;
    }

    private DashboardSteamCsmpResponse fillSteamCsmpResponse() {
        Calendar firstDayOfMonth = Calendar.getInstance();
        Calendar lastDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        DateUtils.setZeroTime(firstDayOfMonth);
        lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateUtils.setZeroTime(lastDayOfMonth);
        List<Steam> steamList = steamRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(firstDayOfMonth.getTime(), lastDayOfMonth.getTime());

        double totalMineralOilCsmp = steamList.stream().filter(m -> Objects.nonNull(m.getMineralOilCsmp())).mapToDouble(Steam::getMineralOilCsmp).sum();
        double totalEsanjorCsmp = steamList.stream().filter(m -> Objects.nonNull(m.getEsanjorCsmp())).mapToDouble(Steam::getEsanjorCsmp).sum();
        double totalNewWharfCsmp = steamList.stream().filter(m -> Objects.nonNull(m.getNewWharfCsmp())).mapToDouble(Steam::getNewWharfCsmp).sum();
        double totalDn40Csmp = steamList.stream().filter(m -> Objects.nonNull(m.getDn40Csmp())).mapToDouble(Steam::getDn40Csmp).sum();
        double totalDn150Csmp = steamList.stream().filter(m -> Objects.nonNull(m.getDn150Csmp())).mapToDouble(Steam::getDn150Csmp).sum();

        DashboardSteamCsmpResponse dashboardSteamResponse = new DashboardSteamCsmpResponse();
        dashboardSteamResponse.setMineralOilCsmp(totalMineralOilCsmp);
        dashboardSteamResponse.setEsanjorCsmp(totalEsanjorCsmp);
        dashboardSteamResponse.setWharfCsmp(totalNewWharfCsmp);
        dashboardSteamResponse.setDn40Csmp(totalDn40Csmp);
        dashboardSteamResponse.setDn150Csmp(totalDn150Csmp);

        return dashboardSteamResponse;
    }
}
