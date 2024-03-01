package com.example.manager.controller;

import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.address.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
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
  @RequestMapping(value = "/findAllAddress/", method = RequestMethod.GET)
  public ResponseEntity<?> getAllAddress() {
    return ResponseEntity.ok(manager.listAllAddresses());
  }

  @ApiOperation(value = "List out the address for Id")
  @io.swagger.annotations.ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Record Found"),
        @ApiResponse(code = 404, message = "Record not found")
      })
  @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getAddressFromId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.addressFromId(id));
  }

  @ApiOperation(value = "Stores the Address")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 201, message = "Record Created")})
  @RequestMapping(value = "/save/", method = RequestMethod.POST)
  public ResponseEntity<?> saveAddress(
      @Valid @RequestBody com.example.manager.data.Address address) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manager.saveAddress(address));
  }

  @ApiOperation(value = "Updates the address details.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record Updated.")})
  @RequestMapping(value = "/updateById/{id}/", method = RequestMethod.PUT)
  public ResponseEntity<?> updateAddress(
      @Valid @RequestBody com.example.manager.data.Address addressa, @PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.updateAddress(addressa, id));
  }

  @ApiOperation(value = "Delete all address.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "All Records deleted.")})
  @RequestMapping(value = "/deleteAll/", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteAllAddress() {
    return ResponseEntity.ok(manager.deleteAllAddress());
  }

  @ApiOperation(value = "Delete address from an Id.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record deleted.")})
  @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.deleteAddress(id));
  }
}
