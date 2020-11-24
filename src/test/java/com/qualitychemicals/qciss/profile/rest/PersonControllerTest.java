package com.qualitychemicals.qciss.profile.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualitychemicals.qciss.profile.converter.PersonConverter;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.rest.v1.PersonController;
import com.qualitychemicals.qciss.profile.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest()
public class PersonControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean PersonConverter personConverter;
    @MockBean PersonService personService;

    /*@Test
    public void updatePersonTest() throws Exception {
        PersonDto personDto= new PersonDto();
        personDto.setFirstName("Rita");
        personDto.setLastName("Jane");
        personDto.setNin("12345de1234");
        personDto.setMobile("0700356304");
        personDto.setResidence("mukono");
        personDto.setGender("Male");
        personDto.setDob(new Date());
        personDto.setEmail("jane@gmail.com");

        String jsonRequest=objectMapper.writeValueAsString(personDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/profile/person/update/{personId}", 77)
        .contentType("application/json")
        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/person/get/{personId}", 3)
        .contentType("application/json")).andExpect(status().isOk());
    }*/

}
