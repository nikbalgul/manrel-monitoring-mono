package com.manrel.manrelmonitoringmono.service.impl;

import com.manrel.manrelmonitoringmono.configuration.JwtTokenProvider;
import com.manrel.manrelmonitoringmono.entity.User;
import com.manrel.manrelmonitoringmono.model.request.AuthenticationRequest;
import com.manrel.manrelmonitoringmono.model.response.AuthenticationResponse;
import com.manrel.manrelmonitoringmono.model.response.UserResponse;
import com.manrel.manrelmonitoringmono.repository.UserRepository;
import com.manrel.manrelmonitoringmono.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthenticationResponse jwtAuthResponse = new AuthenticationResponse();
        jwtAuthResponse.setAccessToken(jwtTokenProvider.generateToken(authentication));

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(user.getUsername());
            userResponse.setName(user.getName());
            userResponse.setRoles(user.getRoles());
            jwtAuthResponse.setUser(userResponse);
        }
        return jwtAuthResponse;
    }
}
