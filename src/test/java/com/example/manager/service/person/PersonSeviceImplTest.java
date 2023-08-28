package com.example.manager.service.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.manager.data.Person;
import com.example.manager.data.ResponseFormat;
import com.example.manager.repository.person.PersonRepository;
import com.example.manager.util.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PersonSeviceImplTest {

    @InjectMocks
    PersonSeviceImpl manager;

    @Mock
    PersonRepository repository;

    @Autowired
    TestData data;

    @Mock
    ResponseFormat response;


    @Test
    void getAllData() {
        List<Person> persons = data.getPersonData(3);

        Mockito.when(repository.findAll()).thenReturn(persons);

        List<Person> testList = manager.getAllData();

        assertEquals(3, testList.size());
    }

    @Test
    void getData(){
        Person person = data.getPersonData(Long.valueOf(1));

        Mockito.when(repository.findById(person.getId())).thenReturn(Optional.of(person));

        Person testPerson = manager.getData(Long.valueOf(1));
        
        assertEquals(testPerson.getId(),person.getId());
        assertEquals(testPerson.getFirstName(), person.getFirstName());
        assertEquals(testPerson.getLastName(), person.getLastName());
        assertEquals(testPerson.getEmailId(), person.getEmailId());
        assertEquals(testPerson.getPhone(),person.getPhone());
    }

    @Test
    void saveData() {
        Person person = data.getPersonData(Long.valueOf(1));
        
        response.setStatus(HttpStatus.CREATED.value());
		response.setMessage("Data Saved Successfully");
		response.setTimeStamp(System.currentTimeMillis());

        when(repository.save(person)).thenReturn(person);

        ResponseFormat testResponse = manager.saveData(person);

        assertEquals(response.getStatus(), testResponse.getStatus());
        assertEquals(response.getMessage(), testResponse.getMessage());
    }

    @Test
    void alterData() {
        Person person = data.getPersonData(Long.valueOf(1));

        response.setStatus(HttpStatus.OK.value());
		response.setMessage("Data Updated Successfully.");
		response.setTimeStamp(System.currentTimeMillis());

        when(repository.save(person)).thenReturn(person);

        ResponseFormat testResponse = manager.alterData(person, person.getId());

        assertEquals(testResponse.getStatus(), response.getStatus());
        assertEquals(testResponse.getMessage(), response.getMessage());
    }

    @Test
    void dropAllData() {
        response.setStatus(HttpStatus.OK.value());
		response.setMessage("All Data Deleted Successfully");
		response.setTimeStamp(System.currentTimeMillis());
 
        ResponseFormat testResponse = manager.dropAllData();

        assertEquals(testResponse.getStatus(), response.getStatus());
        assertEquals(testResponse.getMessage(), response.getMessage());
    }

    @Test
    void dropData() {
        Long id = Long.valueOf(1);

        response.setStatus(HttpStatus.OK.value());
		response.setMessage("Data deleted successfully for : " + id);
		response.setTimeStamp(System.currentTimeMillis());

        ResponseFormat testResponse = manager.dropData(id);

        assertEquals(testResponse.getStatus(), response.getStatus());
        assertEquals(testResponse.getMessage(), response.getMessage());
    }
}