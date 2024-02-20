package com.manrel.manrelmonitoringmono.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveResponse {

    private boolean isValid;
    private String message;
}
