package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.RoleDTO;
import com.qualitychemicals.qciss.profile.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter {
    public RoleDTO entityToDto(Role role){
        RoleDTO roleDTO=new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRole(role.getRole());
        return roleDTO;
    }

    public Role dtoToEntity(RoleDTO roleDTO){
        Role role =new Role();
        role.setId(roleDTO.getId());
        role.setRole(roleDTO.getRole());
        return role;
    }

    public List<RoleDTO> entityToDto(List<Role> roles){
        return roles.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Role> dtoToEntity(List<RoleDTO> roleDTOs){
        return roleDTOs.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
