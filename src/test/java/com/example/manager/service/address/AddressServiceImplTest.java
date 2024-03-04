package com.example.manager.service.address;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.manager.data.Address;
import com.example.manager.data.ResponseFormat;
import com.example.manager.exception.address.AddressNotFoundException;
import com.example.manager.repository.address.AddressRepository;
import com.example.manager.util.TestData;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {

  @InjectMocks AddressServiceImpl manager;

  @Mock AddressRepository repository;

  @Autowired TestData data;

  @Mock ResponseFormat response;

  @Test
  void listAllAddresses() {
    List<Address> address = data.getAddressData(3);

    Mockito.when(repository.findAll()).thenReturn(address);

    List<Address> testAddress = manager.listAllAddresses();

    assertEquals(3, testAddress.size());
  }

  @Test
  void addressFromId() throws AddressNotFoundException {
    Address address = data.getAddressData(Long.valueOf(1));

    Mockito.when(repository.findById(address.getId())).thenReturn(Optional.of(address));

    Address testAddress = manager.addressFromId(Long.valueOf(1));

    assertEquals(address.getId(), testAddress.getId());
    assertEquals(address.getHouseNo(), testAddress.getHouseNo());
    assertEquals(address.getAddressDetails(), testAddress.getAddressDetails());
    assertEquals(address.getLandmark(), testAddress.getLandmark());
    assertEquals(address.getOtherLandmark(), testAddress.getOtherLandmark());
    assertEquals(address.getCity(), testAddress.getCity());
    assertEquals(address.getCountry(), testAddress.getCountry());
    assertEquals(address.getPinCode(), testAddress.getPinCode());
  }

  @Test
  void saveAddress() {
    Address address = data.getAddressData(Long.valueOf(1));

    response.setStatus(HttpStatus.CREATED.value());
    response.setMessage("Record Created");
    response.setTimeStamp(System.currentTimeMillis());

    when(repository.save(address)).thenReturn(address);

    ResponseFormat testResponse = manager.saveAddress(address);

    assertEquals(response.getStatus(), testResponse.getStatus());
    assertEquals(response.getMessage(), testResponse.getMessage());
  }

  @Test
  void updateAddress() {
    Address address = data.getAddressData(Long.valueOf(1));

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("Record Updated");
    response.setTimeStamp(System.currentTimeMillis());

    when(repository.save(address)).thenReturn(address);

    ResponseFormat testResponse = manager.updateAddress(address, address.getId());

    assertEquals(testResponse.getStatus(), response.getStatus());
    assertEquals(testResponse.getMessage(), response.getMessage());
  }

  @Test
  void deleteAddress() {
    Long id = Long.valueOf(1);

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("Record Deleted");
    response.setTimeStamp(System.currentTimeMillis());

    ResponseFormat testResponse = manager.deleteAddress(id);

    assertEquals(testResponse.getStatus(), response.getStatus());
    assertEquals(testResponse.getMessage(), response.getMessage());
  }

  @Test
  void deleteAllAddress() {

    response.setStatus(HttpStatus.OK.value());
    response.setMessage("All records deleted");
    response.setTimeStamp(System.currentTimeMillis());

    ResponseFormat testResponse = manager.deleteAllAddress();

    assertEquals(testResponse.getStatus(), response.getStatus());
    assertEquals(testResponse.getMessage(), response.getMessage());
  }
}
