package com.manrel.manrelmonitoringmono.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PeriodType {

    AYLIK("AYLIK", "Aylık"),
    UCAYLIK("UCAYLIK", "3 Aylık"),
    YILLIK("YILLIK", "Yıllık");

    private final String key;
    private final String name;

    public static PeriodType fromString(String text) {
        for (PeriodType b : PeriodType.values()) {
            if (b.key.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
