package com.manrel.manrelmonitoringmono.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SteamResponse {

    private Long id;

    private String measuredDate;

    private Double mineralOil;

    private Double mineralOilCsmp;

    private Double esanjor;

    private Double esanjorCsmp;

    private Double oldWharf;

    private Double oldWharfCsmp;

    private Double newWharf;

    private Double newWharfCsmp;

    private Double dn40;

    private Double dn40Csmp;

    private Double dn150;

    private Double dn150Csmp;

    private Double sumDn40Dn150;

    private Double sumCsmpDn40Dn150;

    private Double sumCsmp;
}
