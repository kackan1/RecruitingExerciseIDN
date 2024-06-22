package org.example.Utils;

import java.util.Scanner;
import org.example.Person.Person;
import org.example.Person.PersonService;

public class ApplicationLoop {
  private static Scanner scanner = new Scanner(System.in);
  private static String userInput;
  private static boolean looping = true;
  private static PersonService personService = new PersonService();


  public static void loop() {
    System.out.println("Application working!");
    System.out.println("--------------------");
    while (looping) {
      System.out.println("1: to find Employye by id");
      System.out.println("2: create Employee");
      System.out.println("0: exit app");
      System.out.println("--------------------");
      userInput = scanner.nextLine();
      switch (userInput) {
        case "1":
          System.out.print("Type id:");
          System.out.println(personService.findById(scanner.nextLine()));
        case "2":
          Person person = new Person();
          System.out.print("external(t/f):");
          String check = scanner.nextLine();
          //first part of id to know which directory to scan to assign rest of id
          String val = check.equals("t") ? "E" : "I";
          person.setPersonId(val);
          System.out.print("firstname:");
          person.setFirstName(scanner.nextLine());
          System.out.print("lastname:");
          person.setFirstName(scanner.nextLine());
          System.out.print("mobile:");
          person.setFirstName(scanner.nextLine());
          System.out.print("email:");
          person.setFirstName(scanner.nextLine());
          System.out.print("pesel:");
          person.setFirstName(scanner.nextLine());
          personService.create(person);
        case "0":
          looping = false;
      }
    }
  }
}
