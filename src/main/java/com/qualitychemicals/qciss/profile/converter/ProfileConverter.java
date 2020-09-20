package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileConverter {
    @Autowired UserConverter userConverter;
    @Autowired PersonalConverter personalConverter;
    @Autowired WorkConverter workConverter;
    @Autowired AccountConverter accountConverter;
    @Autowired SummaryConverter summaryConverter;
    public ProfileDTO entityToDto(Profile profile){
        ProfileDTO profileDTO=new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setUserDetail(userConverter.entityToDto(profile.getUser()));
        profileDTO.setPersonalInfo(personalConverter.entityToDto(profile.getPersonal()));
        return profileDTO;
    }

    public Profile dtoToEntity(ProfileDTO profileDto){
        Profile profile=new Profile();
        profile.setId(profileDto.getId());
        profile.setUser(userConverter.dtoToEntity(profileDto.getUserDetail()));
        profile.setPersonal(personalConverter.dtoToEntity(profileDto.getPersonalInfo()));
        return profile;

    }

    public List<ProfileDTO> entityToDto(List<Profile> profiles){
        return profiles.stream().map(this::entityToDto).collect(Collectors.toList());

    }


}
