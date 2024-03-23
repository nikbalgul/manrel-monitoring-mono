package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.GreaseBoiler;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.GreaseBoilerRequest;
import com.manrel.manrelmonitoringmono.model.response.GreaseBoilerResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.GreaseBoilerRepository;
import com.manrel.manrelmonitoringmono.service.GreaseBoilerService;
import com.manrel.manrelmonitoringmono.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GreaseBoilerServiceImpl implements GreaseBoilerService {

    private final GreaseBoilerRepository greaseBoilerRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(GreaseBoilerRequest greaseBoilerRequest) {

        if (greaseBoilerRequest.getId() == null && greaseBoilerRepository.existsByMeasuredDate(greaseBoilerRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar cld = Calendar.getInstance();
        cld.setTime(greaseBoilerRequest.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        GreaseBoiler greaseBoiler = new GreaseBoiler();
        greaseBoiler.setId(greaseBoilerRequest.getId());
        greaseBoiler.setMeasuredDate(greaseBoilerRequest.getMeasuredDate());
        greaseBoiler.setBoiler1(greaseBoilerRequest.getBoiler1());
        greaseBoiler.setBoiler1Gnrt(greaseBoilerRequest.getBoiler1Gnrt());
        greaseBoiler.setBoiler2(greaseBoilerRequest.getBoiler2());
        greaseBoiler.setBoiler2Gnrt(greaseBoilerRequest.getBoiler2Gnrt());
        greaseBoiler.setHotOilSteamHour(greaseBoilerRequest.getHotOilSteamHour());
        greaseBoiler.setNaturalGasSteamHour(greaseBoilerRequest.getNaturalGasSteamHour());
        greaseBoiler.setNaturalGasFirstIndex(greaseBoilerRequest.getNaturalGasFirstIndex());
        greaseBoiler.setNaturalGasLastIndex(greaseBoilerRequest.getNaturalGasLastIndex());
        greaseBoiler.setElectricFirstIndex(greaseBoilerRequest.getElectricFirstIndex());
        greaseBoiler.setElectricLastIndex(greaseBoilerRequest.getElectricLastIndex());
        greaseBoiler.setNaturalGasDailyCsmp(greaseBoilerRequest.getNaturalGasLastIndex() - greaseBoilerRequest.getNaturalGasFirstIndex());
        greaseBoiler.setElectricDailyCsmp(greaseBoilerRequest.getElectricLastIndex() - greaseBoilerRequest.getElectricFirstIndex());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        greaseBoiler.setAudit(audit);

        greaseBoilerRepository.save(greaseBoiler);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<GreaseBoilerResponse> getGreaseBoilerList(String period) {

        List<GreaseBoiler> greaseBoilerList = greaseBoilerRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<GreaseBoilerResponse> greaseBoilerResponseList = new ArrayList<>();
        greaseBoilerList.forEach(greaseBoiler -> {
            GreaseBoilerResponse greaseBoilerResponse = new GreaseBoilerResponse();
            greaseBoilerResponse.setId(greaseBoiler.getId());
            greaseBoilerResponse.setBoiler1(greaseBoiler.getBoiler1());
            greaseBoilerResponse.setBoiler1Gnrt(greaseBoiler.getBoiler1Gnrt());
            greaseBoilerResponse.setBoiler2(greaseBoiler.getBoiler2());
            greaseBoilerResponse.setBoiler2Gnrt(greaseBoiler.getBoiler2Gnrt());
            greaseBoilerResponse.setHotOilSteamHour(greaseBoiler.getHotOilSteamHour());
            greaseBoilerResponse.setNaturalGasSteamHour(greaseBoiler.getNaturalGasSteamHour());
            greaseBoilerResponse.setNaturalGasFirstIndex(greaseBoiler.getNaturalGasFirstIndex());
            greaseBoilerResponse.setNaturalGasLastIndex(greaseBoiler.getNaturalGasLastIndex());
            greaseBoilerResponse.setElectricFirstIndex(greaseBoiler.getElectricFirstIndex());
            greaseBoilerResponse.setElectricLastIndex(greaseBoiler.getElectricLastIndex());
            greaseBoilerResponse.setNaturalGasDailyCsmp(greaseBoiler.getNaturalGasDailyCsmp());
            greaseBoilerResponse.setElectricDailyCsmp(greaseBoiler.getElectricDailyCsmp());
            greaseBoilerResponse.setMeasuredDate(formatter.format(greaseBoiler.getMeasuredDate()));
            greaseBoilerResponseList.add(greaseBoilerResponse);
        });
        return greaseBoilerResponseList;
    }

    @Override
    public void delete(Long id) {
        greaseBoilerRepository.deleteById(id);
    }

    private java.util.Date getPeriodDate(String period) {
        PeriodType periodType = PeriodType.fromString(period);

        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DAY_OF_MONTH, 1);
        DateUtils.setZeroTime(cld);
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
