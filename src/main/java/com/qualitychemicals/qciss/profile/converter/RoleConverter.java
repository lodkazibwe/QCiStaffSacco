package com.qualitychemicals.qciss.profile.converter;
import com.qualitychemicals.qciss.profile.dto.RoleDto;
import com.qualitychemicals.qciss.profile.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter {
    public RoleDto entityToDto(Role role){
        RoleDto roleDTO=new RoleDto();
        roleDTO.setId(role.getId());
        roleDTO.setRole(role.getRole());
        return roleDTO;
    }

    public Role dtoToEntity(RoleDto roleDto){
        Role role =new Role();
        role.setId(roleDto.getId());
        role.setRole(roleDto.getRole());
        return role;
    }

    public List<RoleDto> entityToDto(List<Role> roles){
        return roles.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Role> dtoToEntity(List<RoleDto> roleDtos){
        return roleDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
