package com.example.manager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.manager.data.Person;
import com.example.manager.data.ResponseFormat;
import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.person.PersonService;
import com.example.manager.util.TestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class PersonTest {

  @MockBean PersonService manager;

  @InjectMocks Person person;

  @Autowired MockMvc mvc;

  @Autowired TestData test;

  @Autowired ResponseFormat response;

  String uri = UrlMapper.GLOBAL_URL + UrlMapper.PERSON_URL;

  @Test
  void testGetAllData() throws Exception {
    Person person = test.getPersonData(Long.valueOf(1));
    List<Person> persons = Arrays.asList(person);

    Mockito.when(manager.getAllData()).thenReturn(persons);

    mvc.perform(get(uri + "/findAll/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", Matchers.is("John")));
  }

  @Test
  void testGetData() throws Exception {

    Person person = test.getPersonData(Long.valueOf(1));

    Mockito.when(manager.getData(person.getId())).thenReturn(person);

    mvc.perform(get(uri + "/findById/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", Matchers.is("John")))
        .andExpect(jsonPath("$.id", Matchers.is(1)));
  }

  @Test
  void testSaveData() throws JsonProcessingException, Exception {
    Person person = test.getPersonData(Long.valueOf(1));

    response.setStatus(HttpStatus.CREATED.value());
    response.setMessage("Person Created");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.saveData(person)).thenReturn(response);

    mvc.perform(
            post(uri + "/save/")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(person)))
        .andExpect(status().isCreated());
  }

  @Test
  void testUpdateData() throws JsonProcessingException, Exception {
    Person person = test.getPersonData(Long.valueOf(1));

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("Person Data is updated");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.alterData(person, person.getId())).thenReturn(response);

    mvc.perform(
            put(uri + "/updateById/1")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(person)))
        .andExpect(status().isOk());
  }

  @Test
  void testDeleteAllData() throws Exception {
    response.setStatus(HttpStatus.OK.value());
    response.setMessage("All Person Data is deleted");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.dropAllData()).thenReturn(response);

    MvcResult result =
        mvc.perform(delete(uri + "/deleteAll/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", Matchers.is(response.getStatus())))
            .andExpect(jsonPath("$.message", Matchers.is(response.getMessage())))
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    assertThat(responseBody)
        .isEqualToIgnoringWhitespace(new ObjectMapper().writeValueAsString(response));
  }

  @Test
  void testDeleteData() throws Exception {
    Long id = Long.valueOf(1);
    Person person = test.getPersonData(id);

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("Person data for " + id + "  is deleted.");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.dropData(person.getId())).thenReturn(response);

    MvcResult result =
        mvc.perform((delete(uri + "/deleteById/1")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", Matchers.is(response.getStatus())))
            .andExpect(jsonPath("$.message", Matchers.is(response.getMessage())))
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    assertThat(responseBody)
        .isEqualToIgnoringWhitespace(new ObjectMapper().writeValueAsString(response));
  }
}
