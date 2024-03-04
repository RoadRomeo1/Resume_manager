package com.example.manager.controller;

import com.example.manager.data.ResponseFormat;
import com.example.manager.mapping.UrlMapper;
import com.example.manager.service.person.PersonService;
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

@Tag(name = "Person", description = "Person Data Api")
@RestController
@RequestMapping(value = UrlMapper.GLOBAL_URL + UrlMapper.PERSON_URL)
public class Person {

  @Autowired private PersonService manager;

  @Operation(summary = "Fetch all person", description = "Fetches all available person data")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Read Successful")})
  @GetMapping(value = "/findAll/")
  public ResponseEntity<List<com.example.manager.data.Person>> getAllData() {
    return ResponseEntity.ok(manager.getAllData());
  }

  @Operation(
      summary = "Fetch person data for given id",
      description = "Fetches all available person data for given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Read Successful")})
  @GetMapping(value = "/findById/{id}")
  public ResponseEntity<com.example.manager.data.Person> getData(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.getData(id));
  }

  @Operation(summary = "Save person data", description = "Saves given person data")
  @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Write Successful")})
  @PostMapping(value = "/save/")
  public ResponseEntity<ResponseFormat> saveData(
      @Valid @RequestBody com.example.manager.data.Person data) {
    return ResponseEntity.status(HttpStatus.CREATED).body(manager.saveData(data));
  }

  @Operation(summary = "Update person data", description = "Updates person data for given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Update Successful")})
  @PutMapping(value = "/updateById/{id}")
  public ResponseEntity<ResponseFormat> updateData(
      @Valid @RequestBody com.example.manager.data.Person data, @PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.alterData(data, id));
  }

  @Operation(summary = "Delete All person data", description = "Deletes all person data")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Removal Successful")})
  @DeleteMapping(value = "/deleteAll/")
  public ResponseEntity<ResponseFormat> deleteAllData() {
    return ResponseEntity.ok(manager.dropAllData());
  }

  @Operation(summary = "Delete person data", description = "Deletes person data for given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Removal Successful")})
  @DeleteMapping(value = "/deleteById/{id}")
  public ResponseEntity<ResponseFormat> deleteData(@PathVariable("id") Long id) {
    return ResponseEntity.ok(manager.dropData(id));
  }
}
