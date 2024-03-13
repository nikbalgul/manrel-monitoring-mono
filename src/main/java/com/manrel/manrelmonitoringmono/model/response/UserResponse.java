package com.manrel.manrelmonitoringmono.model.response;

import com.manrel.manrelmonitoringmono.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserResponse {

    private String name;
    private String username;
    private Set<Role> roles;
}
