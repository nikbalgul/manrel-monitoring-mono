package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.configuration.JwtTokenProvider;
import com.manrel.manrelmonitoringmono.model.request.AuthenticationRequest;
import com.manrel.manrelmonitoringmono.model.response.AuthenticationResponse;
import com.manrel.manrelmonitoringmono.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthenticationResponse jwtAuthResponse = new AuthenticationResponse();
        jwtAuthResponse.setAccessToken(jwtTokenProvider.generateToken(authentication));

        return jwtAuthResponse;
    }
}
