package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.ProcessNaturalGas;
import com.manrel.manrelmonitoringmono.entity.Steam;
import com.manrel.manrelmonitoringmono.model.response.DashboardProcessResponse;
import com.manrel.manrelmonitoringmono.model.response.DashboardResponse;
import com.manrel.manrelmonitoringmono.model.response.DashboardSteamResponse;
import com.manrel.manrelmonitoringmono.repository.ProcessRepository;
import com.manrel.manrelmonitoringmono.repository.SteamRepository;
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

    @Override
    public DashboardResponse get() {
        DashboardResponse dashboardResponse = new DashboardResponse();

        dashboardResponse.setDashboardSteam(fillSteamResponse());

        dashboardResponse.setDashboardProcess(fillProcessResponse());

        return dashboardResponse;
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

        Map<Integer, Double> mechanicMap = processListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getMechanicCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(ProcessNaturalGas::getMechanicCsmp)));

        Map<Integer, Double> digitalMap = processListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getDigitalCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(ProcessNaturalGas::getDigitalCsmp)));

        Map<Integer, Double> greaseMap = processListOfYear.stream()
                .filter(p -> Objects.nonNull(p.getGreaseCsmp()))
                .collect(Collectors.groupingBy(m -> {
                    Calendar measuredCal = Calendar.getInstance();
                    measuredCal.setTime(m.getMeasuredDate());
                    return measuredCal.get(Calendar.MONTH);
                }, Collectors.summingDouble(ProcessNaturalGas::getGreaseCsmp)));

        DashboardProcessResponse dashboardProcessResponse = new DashboardProcessResponse();
        dashboardProcessResponse.setMechanicMap(mechanicMap);
        dashboardProcessResponse.setDigitalMap(digitalMap);
        dashboardProcessResponse.setGreaseMap(greaseMap);
        return dashboardProcessResponse;
    }

    private DashboardSteamResponse fillSteamResponse() {
        Calendar firstDayOfMonth = Calendar.getInstance();
        Calendar lastDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        DateUtils.setZeroTime(firstDayOfMonth);
        lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateUtils.setZeroTime(lastDayOfMonth);
        List<Steam> steamList = steamRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(firstDayOfMonth.getTime(), lastDayOfMonth.getTime());

        double totalMineralOilCsmp = steamList.stream().filter(m -> Objects.nonNull(m.getMineralOilCsmp())).mapToDouble(Steam::getMineralOilCsmp).sum();
        double totalEsanjorCsmp = steamList.stream().filter(m -> Objects.nonNull(m.getEsanjorCsmp())).mapToDouble(Steam::getEsanjorCsmp).sum();
        double totalWharfCsmp = steamList.stream().filter(m -> Objects.nonNull(m.getWharfCsmp())).mapToDouble(Steam::getWharfCsmp).sum();
        double totalDn40Csmp = steamList.stream().filter(m -> Objects.nonNull(m.getDn40Csmp())).mapToDouble(Steam::getDn40Csmp).sum();
        double totalDn150Csmp = steamList.stream().filter(m -> Objects.nonNull(m.getDn150Csmp())).mapToDouble(Steam::getDn150Csmp).sum();

        DashboardSteamResponse dashboardSteamResponse = new DashboardSteamResponse();
        dashboardSteamResponse.setMineralOilCsmp(totalMineralOilCsmp);
        dashboardSteamResponse.setEsanjorCsmp(totalEsanjorCsmp);
        dashboardSteamResponse.setWharfCsmp(totalWharfCsmp);
        dashboardSteamResponse.setDn40Csmp(totalDn40Csmp);
        dashboardSteamResponse.setDn150Csmp(totalDn150Csmp);

        return dashboardSteamResponse;
    }
}
