package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.ChemicalSalt;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.ChemicalSaltRequest;
import com.manrel.manrelmonitoringmono.model.response.ChemicalSaltResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.ChemicalSaltRepository;
import com.manrel.manrelmonitoringmono.service.ChemicalSaltService;
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
public class ChemicalSaltServiceImpl implements ChemicalSaltService {

    private final ChemicalSaltRepository chemicalSaltRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(ChemicalSaltRequest chemicalSaltRequest) {

        if (chemicalSaltRequest.getId() == null && chemicalSaltRepository.existsByMeasuredDate(chemicalSaltRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar cld = Calendar.getInstance();
        cld.setTime(chemicalSaltRequest.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        List<ChemicalSalt> savedChemicalSaltList = new ArrayList<>();
        ChemicalSalt chemicalSalt = new ChemicalSalt();
        chemicalSalt.setId(chemicalSaltRequest.getId());
        chemicalSalt.setStock4003(chemicalSaltRequest.getStock4003());
        chemicalSalt.setReceived4003(chemicalSaltRequest.getReceived4003());
        chemicalSalt.setUsed4003(chemicalSaltRequest.getUsed4003());
        chemicalSalt.setStock4007(chemicalSaltRequest.getStock4007());
        chemicalSalt.setReceived4007(chemicalSaltRequest.getReceived4007());
        chemicalSalt.setUsed4007(chemicalSaltRequest.getUsed4007());
        chemicalSalt.setStock4010(chemicalSaltRequest.getStock4010());
        chemicalSalt.setReceived4010(chemicalSaltRequest.getReceived4010());
        chemicalSalt.setUsed4010(chemicalSaltRequest.getUsed4010());
        chemicalSalt.setSaltStock(chemicalSaltRequest.getSaltStock());
        chemicalSalt.setSaltReceived(chemicalSaltRequest.getSaltReceived());
        chemicalSalt.setSaltUsed(chemicalSaltRequest.getSaltUsed());
        chemicalSalt.setMeasuredDate(chemicalSaltRequest.getMeasuredDate());
        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        chemicalSalt.setAudit(audit);

        savedChemicalSaltList.add(chemicalSalt);
        chemicalSaltRepository.saveAll(savedChemicalSaltList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<ChemicalSaltResponse> getChemicalSaltList(String period) {

        List<ChemicalSalt> chemicalSaltList = chemicalSaltRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<ChemicalSaltResponse> chemicalSaltResponseList = new ArrayList<>();
        chemicalSaltList.forEach(chemicalSalt -> {
            ChemicalSaltResponse chemicalSaltResponse = new ChemicalSaltResponse();
            chemicalSaltResponse.setId(chemicalSalt.getId());
            chemicalSaltResponse.setStock4003(chemicalSalt.getStock4003());
            chemicalSaltResponse.setReceived4003(chemicalSalt.getReceived4003());
            chemicalSaltResponse.setUsed4003(chemicalSalt.getUsed4003());
            chemicalSaltResponse.setStock4007(chemicalSalt.getStock4007());
            chemicalSaltResponse.setReceived4007(chemicalSalt.getReceived4007());
            chemicalSaltResponse.setUsed4007(chemicalSalt.getUsed4007());
            chemicalSaltResponse.setStock4010(chemicalSalt.getStock4010());
            chemicalSaltResponse.setReceived4010(chemicalSalt.getReceived4010());
            chemicalSaltResponse.setUsed4010(chemicalSalt.getUsed4010());
            chemicalSaltResponse.setSaltStock(chemicalSalt.getSaltStock());
            chemicalSaltResponse.setSaltReceived(chemicalSalt.getSaltReceived());
            chemicalSaltResponse.setSaltUsed(chemicalSalt.getSaltUsed());
            chemicalSaltResponse.setMeasuredDate(formatter.format(chemicalSalt.getMeasuredDate()));
            chemicalSaltResponseList.add(chemicalSaltResponse);
        });
        return chemicalSaltResponseList;
    }

    @Override
    public void delete(Long id) {
        chemicalSaltRepository.deleteById(id);
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
