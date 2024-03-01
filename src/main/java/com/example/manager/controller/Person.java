package com.example.manager.controller;

import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.person.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UrlMapper.GLOBAL_URL + UrlMapper.PERSON_URL)
public class Person {

  @Autowired private PersonService manager;

  @ApiOperation(value = "Get all users", notes = "It will fetch all the users.")
  @io.swagger.annotations.ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Succesful operation"),
        @ApiResponse(code = 404, message = "Record Not Found")
      })
  @RequestMapping(value = "/findAll/", method = RequestMethod.GET)
  public ResponseEntity<?> getAllData() {
    return ResponseEntity.ok(manager.getAllData());
  }

  @ApiOperation(value = "Get the data from Id")
  @io.swagger.annotations.ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful Operation"),
        @ApiResponse(code = 404, message = "Record not found")
      })
  @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getData(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.getData(id));
  }

  @ApiOperation(value = "Save the data")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 201, message = "Record Created")})
  @RequestMapping(value = "/save/", method = RequestMethod.POST)
  public ResponseEntity<?> saveData(@Valid @RequestBody com.example.manager.data.Person data) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manager.saveData(data));
  }

  @ApiOperation(value = "Update the whole record.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record Updated.")})
  @RequestMapping(value = "/updateById/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> updateData(
      @Valid @RequestBody com.example.manager.data.Person data, @PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.alterData(data, id));
  }

  @ApiOperation(value = "Delete all records.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "All Records deleted.")})
  @RequestMapping(value = "/deleteAll/", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteAllData() {
    return ResponseEntity.ok(manager.dropAllData());
  }

  @ApiOperation(value = "Delete records from an Id.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record deleted.")})
  @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteData(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.dropData(id));
  }
}
