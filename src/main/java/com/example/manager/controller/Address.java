package com.example.manager.controller;

import com.example.manager.data.ResponseFormat;
import com.example.manager.exception.address.AddressNotFoundException;
import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.address.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Address", description = "  Address Data Api")
@RestController
@RequestMapping(value = UrlMapper.GLOBAL_URL + UrlMapper.ADDRESS_URL)
public class Address {

  @Autowired private AddressService manager;

  @Operation(summary = "Fetch All Address", description = "Fetches all available address data")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Read Successful")})
  @GetMapping(value = "/findAll/")
  public ResponseEntity<List<com.example.manager.data.Address>> getAllAddress() {
    return ResponseEntity.ok(manager.listAllAddresses());
  }

  @Operation(
      summary = "Fetch Address for given id",
      description = "Fetches all available address data for given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Read Successful")})
  @GetMapping(value = "/findById/{id}")
  public ResponseEntity<com.example.manager.data.Address> getAddressFromId(
      @PathVariable("id") Long id) throws AddressNotFoundException {
    return ResponseEntity.ok(manager.addressFromId(id));
  }

  @Operation(summary = "Save Address", description = "Saves given address data")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Write Successful")})
  @PostMapping(value = "/save/")
  public ResponseEntity<ResponseFormat> saveAddress(
      @Valid @RequestBody com.example.manager.data.Address address) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manager.saveAddress(address));
  }

  @Operation(summary = "Update Address", description = "Updates address data for given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Update Successful")})
  @PutMapping(value = "/updateById/{id}/")
  public ResponseEntity<ResponseFormat> updateAddress(
      @Valid @RequestBody com.example.manager.data.Address address, @PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.updateAddress(address, id));
  }

  @Operation(summary = "Delete All Address", description = "Deletes all address data")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Removal Successful")})
  @DeleteMapping(value = "/deleteAll/")
  public ResponseEntity<ResponseFormat> deleteAllAddress() {
    return ResponseEntity.ok(manager.deleteAllAddress());
  }

  @Operation(summary = "Delete Address", description = "Deletes address data for given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Removal Successful")})
  @DeleteMapping(value = "/deleteById/{id}")
  public ResponseEntity<ResponseFormat> deleteAddress(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.deleteAddress(id));
  }
}
