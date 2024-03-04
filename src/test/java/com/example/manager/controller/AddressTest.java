package com.example.manager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.manager.data.Address;
import com.example.manager.data.ResponseFormat;
import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.address.AddressService;
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
class AddressTest {

  @MockBean AddressService manager;

  @InjectMocks Address address;

  @Autowired MockMvc mvc;

  @Autowired TestData test;

  @Autowired ResponseFormat response;

  String uri = UrlMapper.GLOBAL_URL + UrlMapper.ADDRESS_URL;

  @Test
  void testGetAllAddress() throws Exception {
    Address address = test.getAddressData(Long.valueOf(1));
    List<Address> addresses = Arrays.asList(address);

    Mockito.when(manager.listAllAddresses()).thenReturn(addresses);

    mvc.perform(get(uri + "/findAllAddress/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)))
        .andExpect(jsonPath("$[0].addressDetails", Matchers.is("test street")));
  }

  @Test
  void testGetAddressFromId() throws Exception {
    Address address = test.getAddressData(Long.valueOf(1));

    Mockito.when(manager.addressFromId(address.getId())).thenReturn(address);

    mvc.perform(get(uri + "/findById/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.addressDetails", Matchers.is("test street")))
        .andExpect(jsonPath("$.id", Matchers.is(1)));
  }

  @Test
  void testSaveAddress() throws JsonProcessingException, Exception {

    Address address = test.getAddressData(Long.valueOf(1));

    response.setStatus(HttpStatus.CREATED.value());
    response.setMessage("Address is created");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.saveAddress(address)).thenReturn(response);

    mvc.perform(
            post(uri + "/save/")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(address)))
        .andExpect(status().isCreated());
  }

  @Test
  void testUpdateAddress() throws JsonProcessingException, Exception {

    Address address = test.getAddressData(Long.valueOf(1));

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("Address is updated");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.updateAddress(address, address.getId())).thenReturn(response);

    mvc.perform(
            put(uri + "/updateById/1/")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(address)))
        .andExpect(status().isOk());
  }

  @Test
  void testDeleteAddress() throws Exception {
    Address address = test.getAddressData(Long.valueOf(1));

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("Address with id: " + address.getId() + " is deleted");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.deleteAddress(address.getId())).thenReturn(response);

    MvcResult result =
        mvc.perform(delete(uri + "/deleteById/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", Matchers.is(response.getStatus())))
            .andExpect(jsonPath("$.message", Matchers.is(response.getMessage())))
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    assertThat(responseBody)
        .isEqualToIgnoringWhitespace(new ObjectMapper().writeValueAsString(response));
  }

  @Test
  void testDeleteAllAddress() throws Exception {

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("All address are deleted");
    response.setTimeStamp(System.currentTimeMillis());

    Mockito.when(manager.deleteAllAddress()).thenReturn(response);

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
}
