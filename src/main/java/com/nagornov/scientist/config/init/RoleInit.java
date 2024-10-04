package com.nagornov.scientist.config.init;

import com.nagornov.scientist.model.Role;
import com.nagornov.scientist.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class RoleInit implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        addRoleIfNotExists(new Role(1, "ROLE_USER"));
        addRoleIfNotExists(new Role(2, "ROLE_MANAGER"));
    }

    private void addRoleIfNotExists(Role role) {
        roleRepository.findRoleByName(role.getName()).orElseGet(() -> roleRepository.save(role));
    }
}
