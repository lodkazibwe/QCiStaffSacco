package com.qualitychemicals.qciss.profile.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.rest.v1.UserController;
import com.qualitychemicals.qciss.profile.service.UserService;
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

@WebMvcTest()
public class ProfileControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @MockBean
    UserConverter userConverter;
    @MockBean
    AccountConverter accountConverter;
    @MockBean WorkConverter workConverter;
/*
    @Test
    public void createProfileTest() throws Exception {
        UserDto userDTO =new UserDto();
        PersonDto personDto=new PersonDto();
        personDto.setFirstName("Rita");
        personDto.setLastName("Jane");
        personDto.setNin("12345abcde1234");
        personDto.setMobile("0700356304");
        personDto.setResidence("mukono");
        personDto.setGender("Male");
        personDto.setDob(new Date());
        personDto.setEmail("jane@gmail.com");
        userDTO.setPersonDto(personDto);

        String jsonRequest=objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(post("/profile/profile/register")
                .contentType("application/json")
                .content(jsonRequest)).andExpect(status().isOk());

    }

    @Test
    public void getProfileTest() throws Exception {
        when(userService.getProfile(1)).thenReturn(new Profile());
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/profile/get/{id}",20)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    @Test
    public void getAllTest() throws Exception {
      when(userService.getAll()).thenReturn(Stream.of(new Profile()).collect(Collectors.toList()));
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/profile/getAll")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserAccountsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/profile/getAccounts/{profileID}", 10)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserSummaryTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/profile/getAccount/{profileID}", 1)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getWorkInfoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/profile/getWork/{profileID}", 5)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
*/


    }



