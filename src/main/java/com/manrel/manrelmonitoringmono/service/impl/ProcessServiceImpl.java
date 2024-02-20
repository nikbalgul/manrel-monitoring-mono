package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.ProcessNaturalGas;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.request.ProcessRequest;
import com.manrel.manrelmonitoringmono.model.response.ProcessResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.ProcessRepository;
import com.manrel.manrelmonitoringmono.service.ProcessService;
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
@Transactional()
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;

    private static final Double RADIANT_SM3 = 5.013;
    private static final Double RADIANT_CSMP_SM3 = 2.013;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public SaveResponse saveOrUpdateProcess(ProcessRequest processRequest) {

        if (processRequest.getId() == null && processRepository.existsByMeasuredDate(processRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(processRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(processRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        ProcessNaturalGas previous = processRepository.findByMeasuredDate(previousCld.getTime());
        ProcessNaturalGas next = processRepository.findByMeasuredDate(nextCld.getTime());
        List<ProcessNaturalGas> savedProcessNaturalGasList = new ArrayList<>();
        ProcessNaturalGas processNaturalGas = new ProcessNaturalGas();
        processNaturalGas.setId(processRequest.getId());
        processNaturalGas.setMechanicCounter(processRequest.getMechanicCounter());
        processNaturalGas.setMechanicBar(processRequest.getMechanicBar());
        processNaturalGas.setMechanicHeat(processRequest.getMechanicHeat());
        processNaturalGas.setDigitalV1(processRequest.getDigitalV1());
        processNaturalGas.setDigitalVB1(processRequest.getDigitalVB1());
        processNaturalGas.setDigitalBar(processRequest.getDigitalBar());
        processNaturalGas.setDigitalHeat(processRequest.getDigitalHeat());
        processNaturalGas.setRadiant(processRequest.getRadiant());
        processNaturalGas.setGrease(processRequest.getGrease());
        processNaturalGas.setDiffV1Mc(processRequest.getDigitalV1() - processRequest.getMechanicCounter());
        processNaturalGas.setMeasuredDate(processRequest.getMeasuredDate());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        processNaturalGas.setAudit(audit);

        if (Objects.nonNull(next)) {
            double radiantCsmp = next.getRadiant() - processNaturalGas.getRadiant();
            BigDecimal radiantCsmpBar = BigDecimal.valueOf(radiantCsmp * RADIANT_SM3).setScale(0, RoundingMode.HALF_UP);
            double greaseCsmp = next.getGrease() - processNaturalGas.getGrease();
            BigDecimal greaseCsmpBar = BigDecimal.valueOf(greaseCsmp * RADIANT_CSMP_SM3).setScale(0, RoundingMode.HALF_UP);
            processNaturalGas.setMechanicCsmp(next.getMechanicCounter() - processNaturalGas.getMechanicCounter());
            processNaturalGas.setDigitalCsmp(next.getDigitalVB1() - processNaturalGas.getDigitalVB1());
            processNaturalGas.setRadiantCsmp(radiantCsmp);
            processNaturalGas.setGreaseCsmp(greaseCsmp);
            processNaturalGas.setRadiantCsmpBar(radiantCsmpBar.doubleValue());
            processNaturalGas.setGreaseCsmpBar(greaseCsmpBar.doubleValue());
        }

        if (Objects.nonNull(previous)) {
            double radiantCsmp = processRequest.getRadiant() - previous.getRadiant();
            BigDecimal radiantCsmpBar = BigDecimal.valueOf(radiantCsmp * RADIANT_SM3).setScale(0, RoundingMode.HALF_UP);
            double greaseCsmp = processRequest.getGrease() - previous.getGrease();
            BigDecimal greaseCsmpBar = BigDecimal.valueOf(greaseCsmp * RADIANT_CSMP_SM3).setScale(0, RoundingMode.HALF_UP);
            previous.setMechanicCsmp(processRequest.getMechanicCounter() - previous.getMechanicCounter());
            previous.setDigitalCsmp(processRequest.getDigitalVB1() - previous.getDigitalVB1());
            previous.setRadiantCsmp(radiantCsmp);
            previous.setGreaseCsmp(greaseCsmp);
            previous.setRadiantCsmpBar(radiantCsmpBar.doubleValue());
            previous.setGreaseCsmpBar(greaseCsmpBar.doubleValue());
            savedProcessNaturalGasList.add(previous);
        }

        savedProcessNaturalGasList.add(processNaturalGas);
        processRepository.saveAll(savedProcessNaturalGasList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<ProcessResponse> getProcessList(String period) {

        List<ProcessNaturalGas> processList = processRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<ProcessResponse> processResponseList = new ArrayList<>();
        processList.forEach(processNaturalGas -> {
            ProcessResponse processResponse = new ProcessResponse();
            processResponse.setId(processNaturalGas.getId());
            processResponse.setMechanicCounter(processNaturalGas.getMechanicCounter());
            processResponse.setMechanicBar(processNaturalGas.getMechanicBar());
            processResponse.setMechanicHeat(processNaturalGas.getMechanicHeat());
            processResponse.setMechanicCsmp(processNaturalGas.getMechanicCsmp());
            processResponse.setDigitalV1(processNaturalGas.getDigitalV1());
            processResponse.setDigitalVB1(processNaturalGas.getDigitalVB1());
            processResponse.setDigitalBar(processNaturalGas.getDigitalBar());
            processResponse.setDigitalHeat(processNaturalGas.getDigitalHeat());
            processResponse.setDigitalCsmp(processNaturalGas.getDigitalCsmp());
            processResponse.setRadiant(processNaturalGas.getRadiant());
            processResponse.setRadiantCsmp(processNaturalGas.getRadiantCsmp());
            processResponse.setRadiantCsmpBar(processNaturalGas.getRadiantCsmpBar());
            processResponse.setGrease(processNaturalGas.getGrease());
            processResponse.setGreaseCsmp(processNaturalGas.getGreaseCsmp());
            processResponse.setGreaseCsmpBar(processNaturalGas.getGreaseCsmpBar());
            processResponse.setDiffV1Mc(processNaturalGas.getDiffV1Mc());
            processResponse.setMeasuredDate(formatter.format(processNaturalGas.getMeasuredDate()));
            processResponseList.add(processResponse);
        });
        return processResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        ProcessNaturalGas previousProcess = processRepository.findByMeasuredDate(cld.getTime());
        processRepository.deleteById(request.getId());
        if (Objects.nonNull(previousProcess)) {
            previousProcess.setMechanicCsmp(null);
            previousProcess.setDigitalCsmp(null);
            previousProcess.setRadiantCsmp(null);
            previousProcess.setGreaseCsmp(null);
            previousProcess.setRadiantCsmpBar(null);
            previousProcess.setGreaseCsmpBar(null);
            processRepository.save(previousProcess);
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
