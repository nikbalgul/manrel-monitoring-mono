package com.manrel.manrelmonitoringmono.service;

import com.manrel.manrelmonitoringmono.model.request.AuthenticationRequest;
import com.manrel.manrelmonitoringmono.model.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);
}
