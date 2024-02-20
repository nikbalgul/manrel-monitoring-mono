package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.Steam;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.SteamRequest;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.SteamResponse;
import com.manrel.manrelmonitoringmono.repository.SteamRepository;
import com.manrel.manrelmonitoringmono.service.SteamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SteamServiceImpl implements SteamService {

    private final SteamRepository steamRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(SteamRequest steamRequest) {

        if (steamRepository.existsByMeasuredDate(steamRequest.getMeasuredDate()) && steamRequest.getId() == null) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(steamRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(steamRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        Steam previous = steamRepository.findByMeasuredDate(previousCld.getTime());
        Steam next = steamRepository.findByMeasuredDate(nextCld.getTime());
        Steam steam = new Steam();
        steam.setId(steamRequest.getId());
        steam.setMeasuredDate(steamRequest.getMeasuredDate());
        steam.setMineralOil(steamRequest.getMineralOil());
        steam.setEsanjor(steamRequest.getEsanjor());
        steam.setWharf(steamRequest.getWharf());
        steam.setDn40(steamRequest.getDn40());
        steam.setDn150(steamRequest.getDn150());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        steam.setAudit(audit);

        if (Objects.nonNull(next)) {
            steam.setMineralOilCsmp(next.getMineralOil() - steam.getMineralOil());
            steam.setEsanjorCsmp(next.getEsanjor() - steam.getEsanjor());
            steam.setWharfCsmp(next.getWharf() - steam.getWharf());
            steam.setDn40Csmp(next.getDn40() - steam.getDn40());
            steam.setDn150Csmp(next.getDn150() - steam.getDn150());
            steam.setSumDn40Dn150(steam.getDn40() + steam.getDn150());
            steam.setSumCsmpDn40Dn150(steam.getDn40Csmp() + steam.getDn150Csmp());
            steam.setSumCsmp(steam.getMineralOilCsmp() + steam.getEsanjorCsmp() + steam.getWharfCsmp() + steam.getSumCsmpDn40Dn150());

        }

        List<Steam> savedSteamList = new ArrayList<>();
        if (Objects.nonNull(previous)) {
            previous.setMineralOilCsmp(steamRequest.getMineralOil() - previous.getMineralOil());
            previous.setEsanjorCsmp(steamRequest.getEsanjor() - previous.getEsanjor());
            previous.setWharfCsmp(steamRequest.getWharf() - previous.getWharf());
            previous.setDn40Csmp(steamRequest.getDn40() - previous.getDn40());
            previous.setDn150Csmp(steamRequest.getDn150() - previous.getDn150());
            previous.setSumDn40Dn150(previous.getDn40() + previous.getDn150());
            previous.setSumCsmpDn40Dn150(previous.getDn40Csmp() + previous.getDn150Csmp());
            previous.setSumCsmp(previous.getMineralOilCsmp() + previous.getEsanjorCsmp() + previous.getWharfCsmp() + previous.getSumCsmpDn40Dn150());
            savedSteamList.add(previous);
        }

        savedSteamList.add(steam);
        steamRepository.saveAll(savedSteamList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<SteamResponse> getSteamList(String period) {

        List<Steam> steamList = steamRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<SteamResponse> steamResponseList = new ArrayList<>();
        steamList.forEach(steam -> {
            SteamResponse steamResponse = new SteamResponse();
            steamResponse.setId(steam.getId());
            steamResponse.setMineralOil(steam.getMineralOil());
            steamResponse.setMineralOilCsmp(steam.getMineralOilCsmp());
            steamResponse.setEsanjor(steam.getEsanjor());
            steamResponse.setEsanjorCsmp(steam.getEsanjorCsmp());
            steamResponse.setWharf(steam.getWharf());
            steamResponse.setWharfCsmp(steam.getWharfCsmp());
            steamResponse.setDn40(steam.getDn40());
            steamResponse.setDn40Csmp(steam.getDn40Csmp());
            steamResponse.setDn150(steam.getDn150());
            steamResponse.setDn150Csmp(steam.getDn150Csmp());
            steamResponse.setSumDn40Dn150(steam.getSumDn40Dn150());
            steamResponse.setSumCsmpDn40Dn150(steam.getSumCsmpDn40Dn150());
            steamResponse.setSumCsmp(steam.getSumCsmp());
            steamResponse.setMeasuredDate(formatter.format(steam.getMeasuredDate()));
            steamResponseList.add(steamResponse);
        });
        return steamResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        Steam previousSteam = steamRepository.findByMeasuredDate(cld.getTime());
        steamRepository.deleteById(request.getId());
        if (Objects.nonNull(previousSteam)) {
            previousSteam.setMineralOilCsmp(null);
            previousSteam.setEsanjorCsmp(null);
            previousSteam.setWharfCsmp(null);
            previousSteam.setDn40Csmp(null);
            previousSteam.setDn150Csmp(null);
            previousSteam.setSumDn40Dn150(null);
            previousSteam.setSumCsmpDn40Dn150(null);
            previousSteam.setSumCsmp(null);
            steamRepository.save(previousSteam);
        }
    }

    private java.util.Date getPeriodDate(String period) {
        PeriodType periodType = PeriodType.fromString(period);

        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DAY_OF_MONTH, 1);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        if (PeriodType.UCAYLIK.equals(periodType)) {
            cld.add(Calendar.MONTH, -2);
            return cld.getTime();
        }
        if (PeriodType.YILLIK.equals(periodType)) {
            cld.add(Calendar.MONTH, -11);
            return cld.getTime();
        }
        return cld.getTime();
    }
}
