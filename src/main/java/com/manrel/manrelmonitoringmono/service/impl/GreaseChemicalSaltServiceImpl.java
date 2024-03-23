package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.GreaseChemicalSalt;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.GreaseChemicalSaltRequest;
import com.manrel.manrelmonitoringmono.model.response.GreaseChemicalSaltResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.GreaseChemicalSaltRepository;
import com.manrel.manrelmonitoringmono.service.GreaseChemicalSaltService;
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
public class GreaseChemicalSaltServiceImpl implements GreaseChemicalSaltService {

    private final GreaseChemicalSaltRepository greaseChemicalSaltRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdate(GreaseChemicalSaltRequest greaseChemicalSaltRequest) {

        if (greaseChemicalSaltRequest.getId() == null && greaseChemicalSaltRepository.existsByMeasuredDate(greaseChemicalSaltRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar cld = Calendar.getInstance();
        cld.setTime(greaseChemicalSaltRequest.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        List<GreaseChemicalSalt> savedGreaseChemicalSaltList = new ArrayList<>();
        GreaseChemicalSalt greaseChemicalSalt = new GreaseChemicalSalt();
        greaseChemicalSalt.setId(greaseChemicalSaltRequest.getId());
        greaseChemicalSalt.setStock4003(greaseChemicalSaltRequest.getStock4003());
        greaseChemicalSalt.setReceived4003(greaseChemicalSaltRequest.getReceived4003());
        greaseChemicalSalt.setUsed4003(greaseChemicalSaltRequest.getUsed4003());
        greaseChemicalSalt.setStock4007(greaseChemicalSaltRequest.getStock4007());
        greaseChemicalSalt.setReceived4007(greaseChemicalSaltRequest.getReceived4007());
        greaseChemicalSalt.setUsed4007(greaseChemicalSaltRequest.getUsed4007());
        greaseChemicalSalt.setStock4010(greaseChemicalSaltRequest.getStock4010());
        greaseChemicalSalt.setReceived4010(greaseChemicalSaltRequest.getReceived4010());
        greaseChemicalSalt.setUsed4010(greaseChemicalSaltRequest.getUsed4010());
        greaseChemicalSalt.setUsed1009(greaseChemicalSaltRequest.getUsed1009());
        greaseChemicalSalt.setStock1009(greaseChemicalSaltRequest.getStock1009());
        greaseChemicalSalt.setReceived1009(greaseChemicalSaltRequest.getReceived1009());
        greaseChemicalSalt.setUsed2008(greaseChemicalSaltRequest.getUsed2008());
        greaseChemicalSalt.setStock2008(greaseChemicalSaltRequest.getStock2008());
        greaseChemicalSalt.setReceived2008(greaseChemicalSaltRequest.getReceived2008());
        greaseChemicalSalt.setSaltStock(greaseChemicalSaltRequest.getSaltStock());
        greaseChemicalSalt.setSaltReceived(greaseChemicalSaltRequest.getSaltReceived());
        greaseChemicalSalt.setSaltUsed(greaseChemicalSaltRequest.getSaltUsed());
        greaseChemicalSalt.setMeasuredDate(greaseChemicalSaltRequest.getMeasuredDate());
        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        greaseChemicalSalt.setAudit(audit);

        savedGreaseChemicalSaltList.add(greaseChemicalSalt);
        greaseChemicalSaltRepository.saveAll(savedGreaseChemicalSaltList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<GreaseChemicalSaltResponse> getGreaseChemicalSaltList(String period) {

        List<GreaseChemicalSalt> greaseChemicalSaltList = greaseChemicalSaltRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<GreaseChemicalSaltResponse> greaseChemicalSaltResponseList = new ArrayList<>();
        greaseChemicalSaltList.forEach(greaseChemicalSalt -> {
            GreaseChemicalSaltResponse greaseChemicalSaltResponse = new GreaseChemicalSaltResponse();
            greaseChemicalSaltResponse.setId(greaseChemicalSalt.getId());
            greaseChemicalSaltResponse.setStock4003(greaseChemicalSalt.getStock4003());
            greaseChemicalSaltResponse.setReceived4003(greaseChemicalSalt.getReceived4003());
            greaseChemicalSaltResponse.setUsed4003(greaseChemicalSalt.getUsed4003());
            greaseChemicalSaltResponse.setStock4007(greaseChemicalSalt.getStock4007());
            greaseChemicalSaltResponse.setReceived4007(greaseChemicalSalt.getReceived4007());
            greaseChemicalSaltResponse.setUsed4007(greaseChemicalSalt.getUsed4007());
            greaseChemicalSaltResponse.setStock4010(greaseChemicalSalt.getStock4010());
            greaseChemicalSaltResponse.setReceived4010(greaseChemicalSalt.getReceived4010());
            greaseChemicalSaltResponse.setUsed4010(greaseChemicalSalt.getUsed4010());
            greaseChemicalSaltResponse.setStock1009(greaseChemicalSalt.getStock1009());
            greaseChemicalSaltResponse.setReceived1009(greaseChemicalSalt.getReceived1009());
            greaseChemicalSaltResponse.setUsed1009(greaseChemicalSalt.getUsed1009());
            greaseChemicalSaltResponse.setStock2008(greaseChemicalSalt.getStock2008());
            greaseChemicalSaltResponse.setReceived2008(greaseChemicalSalt.getReceived2008());
            greaseChemicalSaltResponse.setUsed2008(greaseChemicalSalt.getUsed2008());
            greaseChemicalSaltResponse.setSaltStock(greaseChemicalSalt.getSaltStock());
            greaseChemicalSaltResponse.setSaltReceived(greaseChemicalSalt.getSaltReceived());
            greaseChemicalSaltResponse.setSaltUsed(greaseChemicalSalt.getSaltUsed());
            greaseChemicalSaltResponse.setMeasuredDate(formatter.format(greaseChemicalSalt.getMeasuredDate()));
            greaseChemicalSaltResponseList.add(greaseChemicalSaltResponse);
        });
        return greaseChemicalSaltResponseList;
    }

    @Override
    public void delete(Long id) {
        greaseChemicalSaltRepository.deleteById(id);
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
