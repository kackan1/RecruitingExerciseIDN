package org.example.Person;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.Utils.XMLHandler;

public class PersonService implements PersonRepository{
  private final String EXTERNAL_DIRECTORY;
  private final String INTERNAL_DIRECTORY;
  private String currentDir;

  public PersonService(String externalDirectory, String internalDirectory) {
    this.EXTERNAL_DIRECTORY = externalDirectory;
    this.INTERNAL_DIRECTORY = internalDirectory;
  }

  //Iterates over every file in respective directory -- depends on provided id
  //Looks for file containing id and using static XMLHandler class returns Person object
  @Override
  public Person findById(String id) {
    currentDir = id.contains("E") ? EXTERNAL_DIRECTORY : INTERNAL_DIRECTORY;
    File dir = new File(currentDir);
    List<Person> result = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
        .filter(file -> Objects.requireNonNull(XMLHandler.parseFile(file)).getPersonId().equals(id))
        .map(XMLHandler::parseFile)
        .toList();
    if (result.size() == 1){
      System.out.println(result);
      return result.get(0);
    } else {
      System.out.println(("Failed to find Employee with given id."));
      return null;
    }
  }

  @Override
  public List<Person> findByParameter(Map<String, String> parameters) {
    //removes entries with empty value
    parameters.entrySet().removeIf(entry -> entry.getValue().isEmpty());
      currentDir = EXTERNAL_DIRECTORY;
      File dir = new File(currentDir);

    // Filter files and parse them into Person objects
    List<Person> result = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
        .map(XMLHandler::parseFile).filter(Objects::nonNull)
        .filter(person -> personMatcher(parameters, person))
        .toList();
    System.out.println("Records found:");
    result.forEach(System.out::println);
    System.out.println("----------------------------------");
    return result;
  }

  @Override
  public List<Person> findAll() {
    currentDir = EXTERNAL_DIRECTORY;
    File external = new File(currentDir);
    System.out.println("External employees: ");
    System.out.println("----------------------------------------");
    printDirectoryContent(external);
    currentDir = INTERNAL_DIRECTORY;
    File internal = new File(currentDir);
    System.out.println("----------------------------------------");
    System.out.println("Internal employees: ");
    System.out.println("----------------------------------------");
    printDirectoryContent(internal);
    System.out.println("----------------------------------------");
    return null;
  }

  //UI provides first letter part of id
  //after that method finds last used index and after parsing into String
  //creates file with parameters provided in ui using static XMLHandler class
  @Override
  public void create(Person person) {
    currentDir = person.getPersonId().contains("E") ? EXTERNAL_DIRECTORY : INTERNAL_DIRECTORY;
    File dir = new File(currentDir);
    //finds last created file to determine next id
    String lastFile = String.valueOf(
        Arrays.stream(dir.listFiles())
            .max(Comparator.comparing(File::getName))
            .orElse(null));
    //prepareId parses file name into id
    person.setPersonId(prepareId(person.getPersonId(), lastFile));
    XMLHandler.createFile(person, currentDir);
  }

  //Ui provides Person id
  @Override
  public void remove(String id) {
    currentDir = id.contains("E") ? EXTERNAL_DIRECTORY : INTERNAL_DIRECTORY;
    File dir = new File(currentDir);
    List<File> result = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
        .filter(file -> XMLHandler.parseFile(file).getPersonId().equals(id))
        .toList();
    if (result.size() <= 1) {
      File file = result.get(0);
      if (file.delete()){
        System.out.println("Record removed successfully.");
      } else {
        System.out.println("Failed to remove record.");
      }
    } else {
      System.out.println("Record with given id: " + id + " not found.");
    }
  }

  @Override
  public void modify(Person person) {
    currentDir = person.getPersonId().contains("E") ? EXTERNAL_DIRECTORY : INTERNAL_DIRECTORY;
    File dir = new File(currentDir);
    List<File> result = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
        .filter(file -> XMLHandler.parseFile(file).getPersonId().equals(person.getPersonId()))
        .toList();
    if (result.size() == 1) {
      File tmp = result.get(0);
      XMLHandler.modifyFile(tmp, person);
    }
  }

  //from provided path to file extracts id
  //and formats it into correct String
  public String prepareId(String firstC, String fileName){
    String result = firstC;
    Pattern pattern = Pattern.compile("[EI](\\d{3})");
    Matcher matcher = pattern.matcher(fileName);
    if (matcher.find()){
      String numericPart = matcher.group(1);
      int number = Integer.parseInt(numericPart) + 1;
      result = result + String.format("%03d", number);
    } else {
      //if no files in folder exists assign this hardcoded value
      result += "001";
    }
    return result;
  }
  //prints content of directory
  public void printDirectoryContent(File dir){
    Arrays.stream(Objects.requireNonNull(dir.listFiles()))
        .map(XMLHandler::parseFile)
        .toList()
        .forEach(System.out::println);
  }
  //Checks whether person and provided parameters match and returns true if they do
  public boolean personMatcher(Map<String, String> parameters, Person person){
    boolean matches = true;
    // Check each parameter
    for (Map.Entry<String, String> entry : parameters.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();

      if (!value.isEmpty()) {
        switch (key) {
          case "FirstName":
            if (!person.getFirstName().equals(value)) {
              matches = false;
            }
            break;
          case "LastName":
            if (!person.getLastName().equals(value)) {
              matches = false;
            }
            break;
          case "Mobile":
            if (!person.getMobile().equals(value)) {
              matches = false;
            }
            break;
          case "Email":
            if (!person.getEmail().equals(value)) {
              matches = false;
            }
            break;
          case "PESEL":
            if (!person.getPesel().equals(value)) {
              matches = false;
            }
            break;
        }
      }
      if (!matches) {
        break;
      }
    }
    return matches;
  }
}