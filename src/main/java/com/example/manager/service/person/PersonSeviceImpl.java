package com.example.manager.service.person;

import com.example.manager.data.Person;
import com.example.manager.data.ResponseFormat;
import com.example.manager.exception.person.PersonNotFoundException;
import com.example.manager.repository.person.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonSeviceImpl implements PersonService {

  @Autowired private PersonRepository repo;
  @Autowired private ResponseFormat format;

  @Override
  public List<Person> getAllData() {

    List<Person> personDataList = new ArrayList<>();
    repo.findAll().forEach(personDataList::add);
    return personDataList;
  }

  @Override
  public Person getData(Long id) {
    Optional<Person> personData = repo.findById(id);
    if (personData.isEmpty()) {
      throw new PersonNotFoundException("Person with id: " + id + " is not found");
    }
    return personData.get();
  }

  @Override
  public ResponseFormat saveData(Person data) {
    repo.save(data);
    log.error("after saving operation...");
    format.setStatus(HttpStatus.CREATED.value());
    format.setMessage("Data Saved Successfully");
    format.setTimeStamp(System.currentTimeMillis());
    return format;
  }

  @Override
  public ResponseFormat alterData(Person data, Long id) {
    data.setId(id);
    repo.save(data);
    format.setStatus(HttpStatus.OK.value());
    format.setMessage("Data Updated Successfully.");
    format.setTimeStamp(System.currentTimeMillis());
    return format;
  }

  @Override
  public ResponseFormat dropAllData() {
    repo.deleteAll();
    format.setStatus(HttpStatus.OK.value());
    format.setMessage("All Data Deleted Successfully");
    format.setTimeStamp(System.currentTimeMillis());
    return format;
  }

  @Override
  public ResponseFormat dropData(Long id) {
    repo.deleteById(id);
    format.setStatus(HttpStatus.OK.value());
    format.setMessage("Data deleted successfully for : " + id);
    format.setTimeStamp(System.currentTimeMillis());
    return format;
  }
}
