package com.example.demo.controller;

import com.example.demo.mapping.UrlMapper;
import com.example.demo.service.address.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = UrlMapper.globalUrl + UrlMapper.addressUrl)
public class Address {

    @Autowired
    AddressService addressService;

    @ApiOperation(value = "List out all the addresses")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Records Found"), @ApiResponse(code = 404, message = "Records Not Found")})
    @RequestMapping(value = "/findAllAddress", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAddress() {
        return ResponseEntity.ok(addressService.listAllAddresses());
    }

    @ApiOperation(value = "List out the address for Id")
    @io.swagger.annotations.ApiResponses(value = {@ApiResponse(code = 200, message = "Record Found"), @ApiResponse(code = 404, message = "Record not found")})
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressFromId(@PathVariable("id") String id) {
        return ResponseEntity.ok(addressService.addressFromId(id));
    }

    @ApiOperation(value = "Stores the Address")
    @io.swagger.annotations.ApiResponses(value = {@ApiResponse(code = 201, message = "Record Created")})
    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ResponseEntity<?> saveAddress(@Valid @RequestBody com.example.demo.data.Address address) {
        return ResponseEntity.ok(addressService.saveAddress(address));
    }

    @ApiOperation(value = "Updates the address details.")
    @io.swagger.annotations.ApiResponses(value = {@ApiResponse(code = 200, message = "Record Updated.")})
    @RequestMapping(value = "/updateById/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAddress(@Valid @RequestBody com.example.demo.data.Address addressa, @PathVariable("id") String id) {
        return ResponseEntity.ok(addressService.updateAddress(addressa, id));
    }

    @ApiOperation(value = "Delete all address.")
    @io.swagger.annotations.ApiResponses(value = {@ApiResponse(code = 200, message = "All Records deleted.")})
    @RequestMapping(value = "/deleteAll/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllAddress() {
        return ResponseEntity.ok(addressService.deleteAllAddress());
    }

    @ApiOperation(value = "Delete address from an Id.")
    @io.swagger.annotations.ApiResponses(value = {@ApiResponse(code = 200, message = "Record deleted.")})
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@PathVariable("id") String id) {
        return ResponseEntity.ok(addressService.deleteAddress(id));
    }

}
