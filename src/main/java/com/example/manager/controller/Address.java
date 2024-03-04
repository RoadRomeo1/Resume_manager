package com.example.manager.controller;

import com.example.manager.data.ResponseFormat;
import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.address.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = UrlMapper.GLOBAL_URL + UrlMapper.ADDRESS_URL)
public class Address {

  @Autowired private AddressService manager;

  @ApiOperation(value = "List out all the addresses")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Records Found"),
        @ApiResponse(code = 404, message = "Records Not Found")
      })
  @GetMapping(value = "/findAllAddress/")
  public ResponseEntity<List<com.example.manager.data.Address>> getAllAddress() {
    return ResponseEntity.ok(manager.listAllAddresses());
  }

  @ApiOperation(value = "List out the address for Id")
  @io.swagger.annotations.ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Record Found"),
        @ApiResponse(code = 404, message = "Record not found")
      })
  @GetMapping(value = "/findById/{id}")
  public ResponseEntity<com.example.manager.data.Address> getAddressFromId(
      @PathVariable("id") Long id) throws Exception {
    return ResponseEntity.ok(manager.addressFromId(id));
  }

  @ApiOperation(value = "Stores the Address")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 201, message = "Record Created")})
  @PostMapping(value = "/save/")
  public ResponseEntity<ResponseFormat> saveAddress(
      @Valid @RequestBody com.example.manager.data.Address address) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manager.saveAddress(address));
  }

  @ApiOperation(value = "Updates the address details.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record Updated.")})
  @PutMapping(value = "/updateById/{id}/")
  public ResponseEntity<ResponseFormat> updateAddress(
      @Valid @RequestBody com.example.manager.data.Address addressa, @PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.updateAddress(addressa, id));
  }

  @ApiOperation(value = "Delete all address.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "All Records deleted.")})
  @DeleteMapping(value = "/deleteAll/")
  public ResponseEntity<ResponseFormat> deleteAllAddress() {
    return ResponseEntity.ok(manager.deleteAllAddress());
  }

  @ApiOperation(value = "Delete address from an Id.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record deleted.")})
  @DeleteMapping(value = "/deleteById/{id}")
  public ResponseEntity<ResponseFormat> deleteAddress(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.deleteAddress(id));
  }
}
