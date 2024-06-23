package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.example.Person.Person;
import org.example.Utils.XMLHandler;

public class Warmup {
  private static final String DB_PATH="db";
  private static final String EXTERNAL="/external";
  private static final String INTERNAL="/internal";

  public Warmup() {

  }

  public static void warmup(){
    if(Files.notExists((Path.of(DB_PATH))) && Files.notExists(Path.of(DB_PATH + EXTERNAL)) && Files.notExists(
        Path.of(DB_PATH + INTERNAL))){
      try {
        Files.createDirectory(Path.of(DB_PATH));
        Files.createDirectory(Path.of(DB_PATH+EXTERNAL));
        Files.createDirectory(Path.of(DB_PATH+INTERNAL));
        List<Person> externalList = new ArrayList<>();
        List<Person> internalList = new ArrayList<>();
        externalList.add(new Person("E001", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
        externalList.add(new Person("E003", "Michael", "Johnson", "78901234", "m.johnson@example.com", "567890123"));
        externalList.add(new Person("E004", "John", "Brown", "56789012", "john.brown@example.com", "345678901"));
        externalList.add(new Person("E005", "Emily", "Jones", "34567890", "emily.jones@mail.com", "901234567"));
        internalList.add(new Person("I001", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
        internalList.add(new Person("I002", "Jane", "Smith", "45678901", "janesmith@email.com", "987654321"));
        internalList.add(new Person("I003", "Michael", "Johnson", "78901234", "m.johnson@example.com", "567890123"));
        internalList.add(new Person("I005", "David", "Lee", "34567890", "david.lee@mail.com", "901234567"));
        externalList.forEach(person -> XMLHandler.createFile(person, (DB_PATH + EXTERNAL)));
        internalList.forEach(person -> XMLHandler.createFile(person, (DB_PATH + INTERNAL)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
