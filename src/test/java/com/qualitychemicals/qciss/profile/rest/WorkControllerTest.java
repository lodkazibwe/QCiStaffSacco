package com.qualitychemicals.qciss.profile.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.rest.v1.WorkController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WorkController.class)
public class WorkControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean WorkConverter workConverter;


    @Test
    public void updateWorkTest() throws Exception {
        WorkDto workDto=new WorkDto();
        workDto.setSalaryScale("u4");
        workDto.setMonthlySaving(50000);
        workDto.setJobTittle("HR");
        workDto.setCompanyName("QCi");
        workDto.setId(4);
        String jsonRequest =objectMapper.writeValueAsString(workDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/profile/work/update/{workId}",1)
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void getWorkTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/work/get/{workId}",11)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByCompanyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/work/getByCompany/{companyName}","qci")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
