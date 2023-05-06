package com.springboot.nbshop.service.imp;

import com.springboot.nbshop.entity.Role;
import com.springboot.nbshop.reponsitory.RoleRepository;
import com.springboot.nbshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Optional<Role> findRoleById(int id){
        return roleRepository.findById(id);
    }

}
