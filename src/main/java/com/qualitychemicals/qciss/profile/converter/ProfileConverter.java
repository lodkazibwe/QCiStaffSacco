package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.model.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileConverter {
    public ProfileDTO entityToDto(Profile profile){
        ProfileDTO profileDTO=new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setUserDetail(profile.getUser());
        profileDTO.setPersonalInfo(profile.getPersonal());
        profileDTO.setWorkInfo(profile.getWork());
        profileDTO.setAccounts(profile.getAccount());
        profileDTO.setSummary(profile.getSummary());
        return profileDTO;
    }

    public Profile dtoToEntity(ProfileDTO profileDto){
        Profile profile=new Profile();
        profile.setId(profileDto.getId());
        profile.setUser(profileDto.getUserDetail());
        profile.setPersonal(profileDto.getPersonalInfo());
        profile.setWork(profileDto.getWorkInfo());
        profile.setAccount(profileDto.getAccounts());
        profile.setSummary(profileDto.getSummary());
        return profile;

    }

    public List<ProfileDTO> entityToDto(List<Profile> profiles){
        return profiles.stream().map(this::entityToDto).collect(Collectors.toList());

    }




}
