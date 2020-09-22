package com.qualitychemicals.qciss.profile.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualitychemicals.qciss.profile.DTO.AccountDTO;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.rest.v1.AccountRest;
import com.qualitychemicals.qciss.profile.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountRest.class)
public class AccountRestTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean AccountService accountService;
    @MockBean AccountConverter accountConverter;

    @Test
    public void addAccountTest() throws Exception {
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setCategory("mobile Money");
        accountDTO.setAccountNumber("0700456306");
        accountDTO.setAccountName("John");

        String jsonRequest =objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/add/{profileId}", 1)
        .contentType("application/json")
        .content(jsonRequest)).andExpect(status().isOk());
    }

    @Test
    public void getAccountTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/get/{accountId}", 11)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByCategoryTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/getByCat/{category}","mm")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAccountTest() throws Exception {
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setCategory("mobile Money");
        accountDTO.setAccountNumber("0700456306");
        accountDTO.setAccountName("John");

        String jsonRequest =objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/account/update/{accountId}",1)
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isOk());
    }





}
