package com.emphasoft.testtusk.TestTuskFromEmphasoft.service;


import com.emphasoft.testtusk.TestTuskFromEmphasoft.entity.users.Role;
import com.emphasoft.testtusk.TestTuskFromEmphasoft.repository.RoleRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public Role findById(Long roleId) {
        Optional<Role> byId = this.roleRepository.findById(roleId);
        return byId.orElse(null);
    }

    public List<Role> all() {
        return this.roleRepository.findAll();
    }

    public Role save(Role roleSave) {
        if (this.roleRepository.existsByName(roleSave.getName())) {
            return roleSave;
        }

        return roleRepository.save(roleSave);
    }

    public Long delete(Role role) {
        if (!this.roleRepository.existsById(role.getId()))
            return 0L;
        this.roleRepository.delete(role);
        return role.getId();
    }
}
