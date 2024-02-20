package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.BoilerSteam;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.BoilerSteamRequest;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.response.BoilerSteamResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.BoilerSteamRepository;
import com.manrel.manrelmonitoringmono.service.BoilerSteamService;
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
public class BoilerSteamServiceImpl implements BoilerSteamService {

    private final BoilerSteamRepository boilerSteamRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(BoilerSteamRequest boilerSteamRequest) {

        if (boilerSteamRequest.getId() == null && boilerSteamRepository.existsByMeasuredDate(boilerSteamRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(boilerSteamRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(boilerSteamRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        BoilerSteam previousBoilerSteam = boilerSteamRepository.findByMeasuredDate(previousCld.getTime());
        BoilerSteam next = boilerSteamRepository.findByMeasuredDate(nextCld.getTime());

        List<BoilerSteam> savedBoilerSteamList = new ArrayList<>();
        BoilerSteam boilerSteam = new BoilerSteam();
        boilerSteam.setId(boilerSteamRequest.getId());
        boilerSteam.setMeasuredDate(boilerSteamRequest.getMeasuredDate());
        boilerSteam.setBoiler1(boilerSteamRequest.getBoiler1());
        boilerSteam.setBoiler2(boilerSteamRequest.getBoiler2());
        boilerSteam.setErensanBoiler1(boilerSteamRequest.getErensanBoiler1());
        boilerSteam.setErensanBoiler2(boilerSteamRequest.getErensanBoiler2());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        boilerSteam.setAudit(audit);

        if (Objects.nonNull(next)) {
            double gnrt1 = next.getBoiler1() - boilerSteam.getBoiler1();
            double gnrt2 = next.getBoiler2() - boilerSteam.getBoiler2();
            double erensanGnrt1 = next.getErensanBoiler1() - boilerSteam.getErensanBoiler1();
            double erensanGnrt2 = next.getErensanBoiler2() - boilerSteam.getErensanBoiler2();
            boilerSteam.setGnrt1(gnrt1);
            boilerSteam.setGnrt2(gnrt2);
            boilerSteam.setErensanGnrt1(erensanGnrt1);
            boilerSteam.setErensanGnrt2(erensanGnrt2);
            boilerSteam.setSumGnrt(gnrt1 + gnrt2 + erensanGnrt1 + erensanGnrt2);
        }

        if (Objects.nonNull(previousBoilerSteam)) {
            double gnrt1 = boilerSteamRequest.getBoiler1() - previousBoilerSteam.getBoiler1();
            double gnrt2 = boilerSteamRequest.getBoiler2() - previousBoilerSteam.getBoiler2();
            double erensanGnrt1 = boilerSteamRequest.getErensanBoiler1() - previousBoilerSteam.getErensanBoiler1();
            double erensanGnrt2 = boilerSteamRequest.getErensanBoiler2() - previousBoilerSteam.getErensanBoiler2();
            previousBoilerSteam.setGnrt1(gnrt1);
            previousBoilerSteam.setGnrt2(gnrt2);
            previousBoilerSteam.setErensanGnrt1(erensanGnrt1);
            previousBoilerSteam.setErensanGnrt2(erensanGnrt2);
            previousBoilerSteam.setSumGnrt(gnrt1 + gnrt2 + erensanGnrt1 + erensanGnrt2);
            savedBoilerSteamList.add(previousBoilerSteam);
        }

        savedBoilerSteamList.add(boilerSteam);
        boilerSteamRepository.saveAll(savedBoilerSteamList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<BoilerSteamResponse> getBoilerSteamList(String period) {

        List<BoilerSteam> boilerSteamList = boilerSteamRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<BoilerSteamResponse> boilerSteamResponseList = new ArrayList<>();
        boilerSteamList.forEach(boilerSteam -> {
            BoilerSteamResponse boilerSteamResponse = new BoilerSteamResponse();
            boilerSteamResponse.setId(boilerSteam.getId());
            boilerSteamResponse.setBoiler1(boilerSteam.getBoiler1());
            boilerSteamResponse.setBoiler2(boilerSteam.getBoiler2());
            boilerSteamResponse.setErensanBoiler1(boilerSteam.getErensanBoiler1());
            boilerSteamResponse.setErensanBoiler2(boilerSteam.getErensanBoiler2());
            boilerSteamResponse.setGnrt1(boilerSteam.getGnrt1());
            boilerSteamResponse.setGnrt2(boilerSteam.getGnrt2());
            boilerSteamResponse.setErensanGnrt1(boilerSteam.getErensanGnrt1());
            boilerSteamResponse.setErensanGnrt2(boilerSteam.getErensanGnrt2());
            boilerSteamResponse.setSumGnrt(boilerSteam.getSumGnrt());
            boilerSteamResponse.setMeasuredDate(formatter.format(boilerSteam.getMeasuredDate()));
            boilerSteamResponseList.add(boilerSteamResponse);
        });
        return boilerSteamResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        BoilerSteam previousBoilerSteam = boilerSteamRepository.findByMeasuredDate(cld.getTime());
        boilerSteamRepository.deleteById(request.getId());
        if (Objects.nonNull(previousBoilerSteam)) {
            previousBoilerSteam.setGnrt1(null);
            previousBoilerSteam.setGnrt2(null);
            previousBoilerSteam.setErensanGnrt1(null);
            previousBoilerSteam.setErensanGnrt2(null);
            previousBoilerSteam.setSumGnrt(null);
            boilerSteamRepository.save(previousBoilerSteam);
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
