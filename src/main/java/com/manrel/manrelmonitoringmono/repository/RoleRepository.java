package com.manrel.manrelmonitoringmono.repository;

import com.manrel.manrelmonitoringmono.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByConstantName(String constantName);
}
