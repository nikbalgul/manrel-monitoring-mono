package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.entity.Audit;
import com.manrel.manrelmonitoringmono.entity.CanteenNaturalGas;
import com.manrel.manrelmonitoringmono.enumaration.PeriodType;
import com.manrel.manrelmonitoringmono.model.request.CanteenRequest;
import com.manrel.manrelmonitoringmono.model.request.DeleteRequest;
import com.manrel.manrelmonitoringmono.model.response.CanteenResponse;
import com.manrel.manrelmonitoringmono.model.response.SaveResponse;
import com.manrel.manrelmonitoringmono.repository.CanteenRepository;
import com.manrel.manrelmonitoringmono.service.CanteenService;
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
public class CanteenServiceImpl implements CanteenService {

    private final CanteenRepository canteenRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    @Transactional
    public SaveResponse saveOrUpdateCanteen(CanteenRequest canteenRequest) {

        if (canteenRequest.getId() == null && canteenRepository.existsByMeasuredDate(canteenRequest.getMeasuredDate())) {
            return new SaveResponse(false, "Seçilen tarihe ait kayıt girilmiştir.");
        }

        Calendar previousCld = Calendar.getInstance();
        previousCld.setTime(canteenRequest.getMeasuredDate());
        previousCld.add(Calendar.DAY_OF_MONTH, -1);
        Calendar nextCld = Calendar.getInstance();
        nextCld.setTime(canteenRequest.getMeasuredDate());
        nextCld.add(Calendar.DAY_OF_MONTH, 1);
        CanteenNaturalGas previousCanteen = canteenRepository.findByMeasuredDate(previousCld.getTime());
        CanteenNaturalGas next = canteenRepository.findByMeasuredDate(nextCld.getTime());

        List<CanteenNaturalGas> savedCanteenNaturalGasList = new ArrayList<>();
        CanteenNaturalGas canteenNaturalGas = new CanteenNaturalGas();
        canteenNaturalGas.setId(canteenRequest.getId());
        canteenNaturalGas.setMechanicCounter(canteenRequest.getMechanicCounter());
        canteenNaturalGas.setMechanicBar(canteenRequest.getMechanicBar());
        canteenNaturalGas.setMechanicHeat(canteenRequest.getMechanicHeat());
        canteenNaturalGas.setDigitalV2(canteenRequest.getDigitalV2());
        canteenNaturalGas.setDigitalVB2(canteenRequest.getDigitalVB2());
        canteenNaturalGas.setDigitalBar(canteenRequest.getDigitalBar());
        canteenNaturalGas.setDigitalHeat(canteenRequest.getDigitalHeat());
        canteenNaturalGas.setTerminalMechanicCounter(canteenRequest.getTerminalMechanicCounter());
        canteenNaturalGas.setTerminalMechanicBar(canteenRequest.getTerminalMechanicBar());
        canteenNaturalGas.setTerminalMechanicHeat(canteenRequest.getTerminalMechanicHeat());
        canteenNaturalGas.setDiffV1Mc(canteenRequest.getDigitalV2() - canteenRequest.getMechanicCounter());
        canteenNaturalGas.setMeasuredDate(canteenRequest.getMeasuredDate());

        Audit audit = new Audit();
        java.util.Date now = new java.util.Date();
        audit.setCreatedDate(now);
        audit.setLastModifiedDate(now);
        audit.setCreatedBy("admin");
        audit.setLastModifiedBy("admin");
        canteenNaturalGas.setAudit(audit);

        if (Objects.nonNull(next)) {
            canteenNaturalGas.setMechanicCsmp(next.getMechanicCounter() - canteenNaturalGas.getMechanicCounter());
            canteenNaturalGas.setDigitalCsmp(next.getDigitalV2() - canteenNaturalGas.getDigitalV2());
            canteenNaturalGas.setTerminalMechanicCsmp(next.getTerminalMechanicCounter() - canteenNaturalGas.getTerminalMechanicCounter());
            canteenNaturalGas.setKitchenCsmp(canteenNaturalGas.getMechanicCsmp() - canteenNaturalGas.getTerminalMechanicCsmp());
        }

        if (Objects.nonNull(previousCanteen)) {
            previousCanteen.setMechanicCsmp(canteenRequest.getMechanicCounter() - previousCanteen.getMechanicCounter());
            previousCanteen.setDigitalCsmp(canteenRequest.getDigitalV2() - previousCanteen.getDigitalV2());
            previousCanteen.setTerminalMechanicCsmp(canteenRequest.getTerminalMechanicCounter() - previousCanteen.getTerminalMechanicCounter());
            previousCanteen.setKitchenCsmp(previousCanteen.getMechanicCsmp() - previousCanteen.getTerminalMechanicCsmp());
            savedCanteenNaturalGasList.add(previousCanteen);
        }

        savedCanteenNaturalGasList.add(canteenNaturalGas);
        canteenRepository.saveAll(savedCanteenNaturalGasList);
        return new SaveResponse(true, "İşlem Başarılı");
    }

    @Override
    public List<CanteenResponse> getCanteenList(String period) {

        List<CanteenNaturalGas> canteenList = canteenRepository.findByMeasuredDateBetweenOrderByMeasuredDateDesc(getPeriodDate(period), new java.util.Date());
        List<CanteenResponse> canteenResponseList = new ArrayList<>();
        canteenList.forEach(canteenNaturalGas -> {
            CanteenResponse canteenResponse = new CanteenResponse();
            canteenResponse.setId(canteenNaturalGas.getId());
            canteenResponse.setMechanicCounter(canteenNaturalGas.getMechanicCounter());
            canteenResponse.setMechanicBar(canteenNaturalGas.getMechanicBar());
            canteenResponse.setMechanicHeat(canteenNaturalGas.getMechanicHeat());
            canteenResponse.setDigitalV2(canteenNaturalGas.getDigitalV2());
            canteenResponse.setDigitalVB2(canteenNaturalGas.getDigitalVB2());
            canteenResponse.setDigitalBar(canteenNaturalGas.getDigitalBar());
            canteenResponse.setDigitalHeat(canteenNaturalGas.getDigitalHeat());
            canteenResponse.setTerminalMechanicCounter(canteenNaturalGas.getTerminalMechanicCounter());
            canteenResponse.setTerminalMechanicBar(canteenNaturalGas.getTerminalMechanicBar());
            canteenResponse.setTerminalMechanicHeat(canteenNaturalGas.getTerminalMechanicHeat());
            canteenResponse.setKitchenCsmp(canteenNaturalGas.getKitchenCsmp());
            canteenResponse.setDigitalCsmp(canteenNaturalGas.getDigitalCsmp());
            canteenResponse.setMechanicCsmp(canteenNaturalGas.getMechanicCsmp());
            canteenResponse.setTerminalMechanicCsmp(canteenNaturalGas.getTerminalMechanicCsmp());
            canteenResponse.setDiffV1Mc(canteenNaturalGas.getDiffV1Mc());
            canteenResponse.setMeasuredDate(formatter.format(canteenNaturalGas.getMeasuredDate()));
            canteenResponseList.add(canteenResponse);
        });
        return canteenResponseList;
    }

    @Override
    public void delete(DeleteRequest request) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(request.getMeasuredDate());
        cld.add(Calendar.DAY_OF_MONTH, -1);
        CanteenNaturalGas previousCanteen = canteenRepository.findByMeasuredDate(cld.getTime());
        canteenRepository.deleteById(request.getId());
        if (Objects.nonNull(previousCanteen)) {
            previousCanteen.setMechanicCsmp(null);
            previousCanteen.setDigitalCsmp(null);
            previousCanteen.setTerminalMechanicCsmp(null);
            previousCanteen.setKitchenCsmp(null);
            canteenRepository.save(previousCanteen);
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
