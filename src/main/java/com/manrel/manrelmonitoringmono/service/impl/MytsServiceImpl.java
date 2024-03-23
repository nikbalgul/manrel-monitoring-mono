package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.Myts;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.MytsRequest;
import com.manrel.manrelmonitoringmono.model.response.MytsResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.MytsRepository;
import com.manrel.manrelmonitoringmono.service.MytsService;
import com.manrel.manrelmonitoringmono.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MytsServiceImpl implements MytsService {

    private final MytsRepository mytsRepository;

    private static final Integer FULL_DAY = 24;
    private static final Integer LOAD_RATE_CONST = 6700;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(MytsRequest mytsRequest) {

        if (mytsRequest.getId() == null && mytsRepository.existsByMeasuredDate(mytsRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(mytsRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(mytsRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        Myts previousMyts = mytsRepository.findByMeasuredDate(previousCld.getTime());
        Myts next = mytsRepository.findByMeasuredDate(nextCld.getTime());
        Myts myts = new Myts();
        myts.setId(mytsRequest.getId());
        myts.setHour1Value(mytsRequest.getHour1Value());
        myts.setHour2Value(mytsRequest.getHour2Value());
        myts.setHour3Value(mytsRequest.getHour3Value());
        myts.setMeasuredDate(mytsRequest.getMeasuredDate());
        myts.setDiffHour2Hour1(mytsRequest.getHour2Value() - mytsRequest.getHour1Value());
        myts.setDiffHour3Hour2(mytsRequest.getHour3Value() - mytsRequest.getHour2Value());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        myts.setAudit(audit);

        if (Objects.nonNull(next)) {
            double dailySumCsmp = next.getHour1Value() - myts.getHour1Value();
            double dailySumHour = dailySumCsmp / FULL_DAY;
            double loadRate = dailySumHour / LOAD_RATE_CONST;
            BigDecimal scaledDailySumHour = BigDecimal.valueOf(dailySumHour).setScale(0, RoundingMode.HALF_UP);
            BigDecimal scaledLoadRate = BigDecimal.valueOf(loadRate).setScale(4, RoundingMode.HALF_UP);
            myts.setLoadRate(scaledLoadRate.doubleValue());
            myts.setDailySumHour(scaledDailySumHour.doubleValue());
            myts.setDailySumCsmp(dailySumCsmp);
            myts.setDiffNextHour1Hour3(next.getHour1Value() - myts.getHour3Value());
        }

        List<Myts> savedMytsNaturalGasList = new ArrayList<>();
        if (Objects.nonNull(previousMyts)) {
            double dailySumCsmp = mytsRequest.getHour1Value() - previousMyts.getHour1Value();
            double dailySumHour = dailySumCsmp / FULL_DAY;
            double loadRate = dailySumHour / LOAD_RATE_CONST;
            BigDecimal scaledDailySumHour = BigDecimal.valueOf(dailySumHour).setScale(0, RoundingMode.HALF_UP);
            BigDecimal scaledLoadRate = BigDecimal.valueOf(loadRate).setScale(4, RoundingMode.HALF_UP);
            previousMyts.setLoadRate(scaledLoadRate.doubleValue());
            previousMyts.setDailySumHour(scaledDailySumHour.doubleValue());
            previousMyts.setDailySumCsmp(dailySumCsmp);
            previousMyts.setDiffNextHour1Hour3(mytsRequest.getHour1Value() - previousMyts.getHour3Value());
            savedMytsNaturalGasList.add(previousMyts);
        }

        savedMytsNaturalGasList.add(myts);
        mytsRepository.saveAll(savedMytsNaturalGasList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<MytsResponse> getMytsList(String period) {

        List<Myts> mytsList = mytsRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<MytsResponse> mytsResponseList = new ArrayList<>();
        mytsList.forEach(myts -> {
            MytsResponse mytsResponse = new MytsResponse();
            mytsResponse.setId(myts.getId());
            mytsResponse.setHour1Value(myts.getHour1Value());
            mytsResponse.setHour2Value(myts.getHour2Value());
            mytsResponse.setHour3Value(myts.getHour3Value());
            mytsResponse.setDiffHour2Hour1(myts.getDiffHour2Hour1());
            mytsResponse.setDiffHour3Hour2(myts.getDiffHour3Hour2());
            mytsResponse.setDiffNextHour1Hour3(myts.getDiffNextHour1Hour3());
            mytsResponse.setDiffNextHour1Hour3(myts.getDiffNextHour1Hour3());
            mytsResponse.setDailySumCsmp(myts.getDailySumCsmp());
            mytsResponse.setDailySumHour(myts.getDailySumHour());
            Double loadRate = myts.getLoadRate();
            mytsResponse.setLoadRate("");
            if (Objects.nonNull(loadRate)) {
                BigDecimal scaledLoadRate = BigDecimal.valueOf(loadRate * 100).setScale(2, RoundingMode.HALF_UP);
                mytsResponse.setLoadRate(scaledLoadRate + "%");
            }
            mytsResponse.setMeasuredDate(formatter.format(myts.getMeasuredDate()));
            mytsResponseList.add(mytsResponse);
        });
        return mytsResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        DateUtils.setZeroTime(cld);
        Myts previousMyts = mytsRepository.findByMeasuredDate(cld.getTime());
        mytsRepository.deleteById(request.getId());
        if (Objects.nonNull(previousMyts)) {
            previousMyts.setLoadRate(null);
            previousMyts.setDailySumHour(null);
            previousMyts.setDailySumCsmp(null);
            previousMyts.setDiffNextHour1Hour3(null);
            mytsRepository.save(previousMyts);
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
