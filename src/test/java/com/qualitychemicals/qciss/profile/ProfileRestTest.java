package com.qualitychemicals.qciss.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualitychemicals.qciss.profile.DTO.PersonalDTO;
import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.DTO.UserDTO;
import com.qualitychemicals.qciss.profile.converter.ProfileConverter;
import com.qualitychemicals.qciss.profile.rest.v1.ProfileRest;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileRest.class)
public class ProfileRestTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean
    ProfileService profileService;
    @MockBean
    ProfileConverter profileConverter;
    @Test
    public void createProfileTest() throws Exception {
        ProfileDTO profileDTO=new ProfileDTO();
        UserDTO userDetail=new UserDTO();
        userDetail.setUserName("user1");
        userDetail.setPassword("1234abcd");
        Date date=new Date();
        PersonalDTO personalInfo=new PersonalDTO();
        personalInfo.setFirstName("Rita");
        personalInfo.setLastName("Jane");
        personalInfo.setNin("12345abcde1234");
        personalInfo.setContact("0700356304");
        personalInfo.setResidence("mukono");
        personalInfo.setDob(date);
        personalInfo.setGender("Male");
        personalInfo.setEmail("jane@gmail.com");
        profileDTO.setUserDetail(userDetail);
        profileDTO.setPersonalInfo(personalInfo);


        String jsonRequest=objectMapper.writeValueAsString(profileDTO);
        mockMvc.perform(post("/profile/create")
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isOk());

    }

    @Test
    public void getProfileTest() throws Exception {
        mockMvc.perform(get("/profile/get/{id}","a")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    public void getAllTest() throws Exception {
        mockMvc.perform(get("/profile/getAll")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    

    }



