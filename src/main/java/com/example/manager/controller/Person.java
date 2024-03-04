package com.example.manager.controller;

import com.example.manager.data.ResponseFormat;
import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.person.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  @GetMapping(value = "/findAll/")
  public ResponseEntity<List<com.example.manager.data.Person>> getAllData() {
    return ResponseEntity.ok(manager.getAllData());
  }

  @ApiOperation(value = "Get the data from Id")
  @io.swagger.annotations.ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful Operation"),
        @ApiResponse(code = 404, message = "Record not found")
      })
  @GetMapping(value = "/findById/{id}")
  public ResponseEntity<com.example.manager.data.Person> getData(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.getData(id));
  }

  @ApiOperation(value = "Save the data")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 201, message = "Record Created")})
  @PostMapping(value = "/save/")
  public ResponseEntity<ResponseFormat> saveData(
      @Valid @RequestBody com.example.manager.data.Person data) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manager.saveData(data));
  }

  @ApiOperation(value = "Update the whole record.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record Updated.")})
  @PutMapping(value = "/updateById/{id}")
  public ResponseEntity<ResponseFormat> updateData(
      @Valid @RequestBody com.example.manager.data.Person data, @PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.alterData(data, id));
  }

  @ApiOperation(value = "Delete all records.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "All Records deleted.")})
  @DeleteMapping(value = "/deleteAll/")
  public ResponseEntity<ResponseFormat> deleteAllData() {
    return ResponseEntity.ok(manager.dropAllData());
  }

  @ApiOperation(value = "Delete records from an Id.")
  @io.swagger.annotations.ApiResponses(
      value = {@ApiResponse(code = 200, message = "Record deleted.")})
  @DeleteMapping(value = "/deleteById/{id}")
  public ResponseEntity<ResponseFormat> deleteData(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.dropData(id));
  }
}
