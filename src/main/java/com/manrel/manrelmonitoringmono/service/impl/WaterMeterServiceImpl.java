package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.WaterMeter;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.WaterMeterRequest;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.WaterMeterResponse;
import com.manrel.manrelmonitoringmono.repository.WaterMeterRepository;
import com.manrel.manrelmonitoringmono.service.WaterMeterService;
import com.manrel.manrelmonitoringmono.util.DateUtils;
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
public class WaterMeterServiceImpl implements WaterMeterService {

    private final WaterMeterRepository waterMeterRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(WaterMeterRequest waterMeterRequest) {

        if (waterMeterRequest.getId() == null && waterMeterRepository.existsByMeasuredDate(waterMeterRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(waterMeterRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(waterMeterRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        WaterMeter previous = waterMeterRepository.findByMeasuredDate(previousCld.getTime());
        WaterMeter next = waterMeterRepository.findByMeasuredDate(nextCld.getTime());
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(waterMeterRequest.getId());
        waterMeter.setMeasuredDate(waterMeterRequest.getMeasuredDate());
        waterMeter.setMainCounter(waterMeterRequest.getMainCounter());
        waterMeter.setBoilerCounter(waterMeterRequest.getBoilerCounter());
        waterMeter.setHotWaterCounter(waterMeterRequest.getHotWaterCounter());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        waterMeter.setAudit(audit);

        if (Objects.nonNull(next)) {
            waterMeter.setMainCsmp(next.getMainCounter() - waterMeter.getMainCounter());
            waterMeter.setBoilerCsmp(next.getBoilerCounter() - waterMeter.getBoilerCounter());
            waterMeter.setHotWaterCsmp(next.getHotWaterCounter() - waterMeter.getHotWaterCounter());
            waterMeter.setOther(waterMeter.getMainCsmp() - (waterMeter.getBoilerCsmp() + waterMeter.getHotWaterCsmp()));
        }

        List<WaterMeter> savedWaterMeterList = new ArrayList<>();
        if (Objects.nonNull(previous)) {
            previous.setMainCsmp(waterMeter.getMainCounter() - previous.getMainCounter());
            previous.setBoilerCsmp(waterMeter.getBoilerCounter() - previous.getBoilerCounter());
            previous.setHotWaterCsmp(waterMeter.getHotWaterCounter() - previous.getHotWaterCounter());
            previous.setOther(previous.getMainCsmp() - (previous.getBoilerCsmp() + previous.getHotWaterCsmp()));
            savedWaterMeterList.add(previous);
        }

        savedWaterMeterList.add(waterMeter);
        waterMeterRepository.saveAll(savedWaterMeterList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<WaterMeterResponse> getWaterMeterList(String period) {

        List<WaterMeter> waterMeterList = waterMeterRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<WaterMeterResponse> waterMeterResponseList = new ArrayList<>();
        waterMeterList.forEach(waterMeter -> {
            WaterMeterResponse waterMeterResponse = new WaterMeterResponse();
            waterMeterResponse.setId(waterMeter.getId());
            waterMeterResponse.setMainCounter(waterMeter.getMainCounter());
            waterMeterResponse.setMainCsmp(waterMeter.getMainCsmp());
            waterMeterResponse.setBoilerCounter(waterMeter.getBoilerCounter());
            waterMeterResponse.setBoilerCsmp(waterMeter.getBoilerCsmp());
            waterMeterResponse.setHotWaterCounter(waterMeter.getHotWaterCounter());
            waterMeterResponse.setHotWaterCsmp(waterMeter.getHotWaterCsmp());
            waterMeterResponse.setOther(waterMeter.getOther());
            waterMeterResponse.setMeasuredDate(formatter.format(waterMeter.getMeasuredDate()));
            waterMeterResponseList.add(waterMeterResponse);
        });
        return waterMeterResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        DateUtils.setZeroTime(cld);
        WaterMeter previousWaterMeter = waterMeterRepository.findByMeasuredDate(cld.getTime());
        waterMeterRepository.deleteById(request.getId());
        if (Objects.nonNull(previousWaterMeter)) {
            previousWaterMeter.setMainCsmp(null);
            previousWaterMeter.setBoilerCsmp(null);
            previousWaterMeter.setHotWaterCsmp(null);
            previousWaterMeter.setOther(null);
            waterMeterRepository.save(previousWaterMeter);
        }
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
