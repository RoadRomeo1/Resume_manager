package com.example.manager.service.person;

import com.example.manager.data.Person;
import com.example.manager.data.ResponseFormat;
import java.util.List;

public interface PersonService {

  public List<Person> getAllData();

  public Person getData(Long Id);

  public ResponseFormat saveData(Person personData);

  public ResponseFormat alterData(Person personData, Long Id);

  public ResponseFormat dropAllData();

  public ResponseFormat dropData(Long Id);
}
