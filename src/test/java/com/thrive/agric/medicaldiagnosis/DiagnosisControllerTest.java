package com.thrive.agric.medicaldiagnosis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thrive.agric.medicaldiagnosis.controller.DiagnosisController;
import com.thrive.agric.medicaldiagnosis.domain.DiagnosisRequest;
import com.thrive.agric.medicaldiagnosis.domain.SpecialisationRequest;
import com.thrive.agric.medicaldiagnosis.service.DiagnosisService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)

public class DiagnosisControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    DiagnosisController controller;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Mock
    private DiagnosisService service;

    @Test
    public void postDiagnosis() throws Exception {
        DiagnosisRequest request = new DiagnosisRequest();
        request.setAccuracy(100);
        request.setProfName("kolokolo");
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(request);
        Mockito.when(service.postData(request)).thenReturn(request);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/diagnosis/issue")
                .accept(MediaType.APPLICATION_JSON)
                .content(data))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void postSpecilisation() throws Exception {
        SpecialisationRequest request = new SpecialisationRequest();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(request);
        Mockito.when(service.saveSpecilisationData(request)).thenReturn(request);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/diagnosis/specilisation")
                .accept(MediaType.APPLICATION_JSON)
                .content(data))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
