package org.example.Person;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.example.Utils.XMLHandler;

public class PersonService implements PersonRepository{
  private final String EXTERNAL_DIRECTORY = "db/external";
  private final String INTERNAL_DIRECTORY = "db/internal";
  private String currentDir = EXTERNAL_DIRECTORY;

  //Iterates over every file in respective directory -- depends on provided id
  //Looks for file containing id and returns Person object
  @Override
  public Person findById(String id) {
    currentDir = id.contains("E") ? EXTERNAL_DIRECTORY : INTERNAL_DIRECTORY;
    File dir = new File(currentDir);
    List<Person> result = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
        .filter(file -> XMLHandler.parseFile(file).getPersonId().equals(id))
        .map(XMLHandler::parseFile)
        .toList();
    if (result.size() == 1){
      return result.get(0);
    } else {
      throw new RuntimeException("Employee with given id not found");
    }
  }

  @Override
  public List<Person> findByParameter(Map<String, String> parameters) {
    return null;
  }

  @Override
  public List<Person> findAll() {
    return null;
  }

  @Override
  public void create(Person person) {
    currentDir = person.getPersonId().contains("E") ? EXTERNAL_DIRECTORY : INTERNAL_DIRECTORY;
    File dir = new File(currentDir);
    String lastFile = String.valueOf(
        Arrays.stream(dir.listFiles())
            .max(Comparator.comparing(File::getName)));
    lastFile.replaceFirst("\\D", "");
    long idToAssign = Long.valueOf(lastFile) + 1;
    person.setPersonId(person.getPersonId() + idToAssign);
    XMLHandler.createFile(person, currentDir);
  }

  @Override
  public void remove(String id) {

  }

  @Override
  public void modify(Person person) {

  }
}