package com.example.manager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SmokeTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void healthEndpointShouldBeUp() throws Exception {
    mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
  }

  @Test
  void personFindAllShouldBeOk() throws Exception {
    mockMvc.perform(get("/api/v1/manage/person/findAll/")).andExpect(status().isOk());
  }

  // Add more smoke tests for other critical endpoints as needed
}
