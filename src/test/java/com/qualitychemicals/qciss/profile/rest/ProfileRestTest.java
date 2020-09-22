package com.qualitychemicals.qciss.profile.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualitychemicals.qciss.profile.DTO.PersonalDTO;
import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.DTO.UserDTO;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.ProfileConverter;
import com.qualitychemicals.qciss.profile.converter.SummaryConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.rest.v1.ProfileRest;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileRest.class)
public class ProfileRestTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean ProfileService profileService;
    @MockBean ProfileConverter profileConverter;
    @MockBean AccountConverter accountConverter;
    @MockBean SummaryConverter summaryConverter;
    @MockBean WorkConverter workConverter;

    @Test
    public void createProfileTest() throws Exception {
        ProfileDTO profileDTO=new ProfileDTO();
        UserDTO userDetail=new UserDTO();
        userDetail.setUserName("user1");
        userDetail.setPassword("1234abcd");
        PersonalDTO personalInfo=new PersonalDTO();
        personalInfo.setFirstName("Rita");
        personalInfo.setLastName("Jane");
        personalInfo.setNin("12345abcde1234");
        personalInfo.setContact("0700356304");
        personalInfo.setResidence("mukono");
        personalInfo.setGender("Male");
        personalInfo.setDob(new Date());
        personalInfo.setEmail("jane@gmail.com");
        profileDTO.setUserDetail(userDetail);
        profileDTO.setPersonalInfo(personalInfo);

        String jsonRequest=objectMapper.writeValueAsString(profileDTO);
        mockMvc.perform(post("/profile/create")
                .contentType("application/json")
                .content(jsonRequest)).andExpect(status().isOk());

    }

    @Test
    public void getProfileTest() throws Exception {
        when(profileService.getProfile(1)).thenReturn(new Profile());
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/get/{id}",20)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    public void getAllTest() throws Exception {
      when(profileService.getAll()).thenReturn(Stream.of(new Profile()).collect(Collectors.toList()));
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/getAll")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserAccountsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/account/get/{profileID}", 10)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserSummaryTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/summary/get/{profileID}", 1)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getWorkInfoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/workInfo/get/{profileID}", 5)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }



    }



