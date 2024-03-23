package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.Tts;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.TtsRequest;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.model.response.TtsResponse;
import com.manrel.manrelmonitoringmono.repository.TtsRepository;
import com.manrel.manrelmonitoringmono.service.TtsService;
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
public class TtsServiceImpl implements TtsService {

    private final TtsRepository ttsRepository;

    private static final Integer FULL_DAY = 24;
    private static final Integer LOAD_RATE_CONST = 6700;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(TtsRequest ttsRequest) {

        if (ttsRequest.getId() == null && ttsRepository.existsByMeasuredDate(ttsRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(ttsRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(ttsRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        Tts previous = ttsRepository.findByMeasuredDate(previousCld.getTime());
        Tts next = ttsRepository.findByMeasuredDate(nextCld.getTime());
        Tts tts = new Tts();
        tts.setId(ttsRequest.getId());
        tts.setHour1Value(ttsRequest.getHour1Value());
        tts.setHour2Value(ttsRequest.getHour2Value());
        tts.setHour3Value(ttsRequest.getHour3Value());
        tts.setMeasuredDate(ttsRequest.getMeasuredDate());
        tts.setDiffHour2Hour1(ttsRequest.getHour2Value() - ttsRequest.getHour1Value());
        tts.setDiffHour3Hour2(ttsRequest.getHour3Value() - ttsRequest.getHour2Value());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        tts.setAudit(audit);

        if (Objects.nonNull(next)) {
            double dailySumCsmp = next.getHour1Value() - tts.getHour1Value();
            double dailySumHour = dailySumCsmp / FULL_DAY;
            double loadRate = dailySumHour / LOAD_RATE_CONST;
            BigDecimal scaledDailySumHour = BigDecimal.valueOf(dailySumHour).setScale(0, RoundingMode.HALF_UP);
            BigDecimal scaledLoadRate = BigDecimal.valueOf(loadRate).setScale(4, RoundingMode.HALF_UP);
            tts.setLoadRate(scaledLoadRate.doubleValue());
            tts.setDailySumHour(scaledDailySumHour.doubleValue());
            tts.setDailySumCsmp(dailySumCsmp);
            tts.setDiffNextHour1Hour3(next.getHour1Value() - tts.getHour3Value());
        }

        List<Tts> savedTtsNaturalGasList = new ArrayList<>();
        if (Objects.nonNull(previous)) {
            double dailySumCsmp = tts.getHour1Value() - previous.getHour1Value();
            double dailySumHour = dailySumCsmp / FULL_DAY;
            double loadRate = dailySumHour / LOAD_RATE_CONST;
            BigDecimal scaledDailySumHour = BigDecimal.valueOf(dailySumHour).setScale(0, RoundingMode.HALF_UP);
            BigDecimal scaledLoadRate = BigDecimal.valueOf(loadRate).setScale(4, RoundingMode.HALF_UP);
            previous.setLoadRate(scaledLoadRate.doubleValue());
            previous.setDailySumHour(scaledDailySumHour.doubleValue());
            previous.setDailySumCsmp(dailySumCsmp);
            previous.setDiffNextHour1Hour3(tts.getHour1Value() - previous.getHour3Value());
            savedTtsNaturalGasList.add(previous);
        }

        savedTtsNaturalGasList.add(tts);
        ttsRepository.saveAll(savedTtsNaturalGasList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<TtsResponse> getTtsList(String period) {

        List<Tts> ttsList = ttsRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<TtsResponse> ttsResponseList = new ArrayList<>();
        ttsList.forEach(tts -> {
            TtsResponse ttsResponse = new TtsResponse();
            ttsResponse.setId(tts.getId());
            ttsResponse.setHour1Value(tts.getHour1Value());
            ttsResponse.setHour2Value(tts.getHour2Value());
            ttsResponse.setHour3Value(tts.getHour3Value());
            ttsResponse.setDiffHour2Hour1(tts.getDiffHour2Hour1());
            ttsResponse.setDiffHour3Hour2(tts.getDiffHour3Hour2());
            ttsResponse.setDiffNextHour1Hour3(tts.getDiffNextHour1Hour3());
            ttsResponse.setDiffNextHour1Hour3(tts.getDiffNextHour1Hour3());
            ttsResponse.setDailySumCsmp(tts.getDailySumCsmp());
            ttsResponse.setDailySumHour(tts.getDailySumHour());
            Double loadRate = tts.getLoadRate();
            ttsResponse.setLoadRate("");
            if (Objects.nonNull(loadRate)) {
                BigDecimal scaledLoadRate = BigDecimal.valueOf(loadRate * 100).setScale(2, RoundingMode.HALF_UP);
                ttsResponse.setLoadRate(scaledLoadRate + "%");
            }
            ttsResponse.setMeasuredDate(formatter.format(tts.getMeasuredDate()));
            ttsResponseList.add(ttsResponse);
        });
        return ttsResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        DateUtils.setZeroTime(cld);
        Tts previousTts = ttsRepository.findByMeasuredDate(cld.getTime());
        ttsRepository.deleteById(request.getId());
        if (Objects.nonNull(previousTts)) {
            previousTts.setLoadRate(null);
            previousTts.setDailySumHour(null);
            previousTts.setDailySumCsmp(null);
            previousTts.setDiffNextHour1Hour3(null);
            ttsRepository.save(previousTts);
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
