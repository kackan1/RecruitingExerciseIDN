package org.example.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonRepository {
  Person findById(String id);
  List<Person> findByParameter(Map<String, String> parameters);
  List<Person> findAll();
  void create(Person person);
  void remove(String id);
  void modify(Person person);
}
