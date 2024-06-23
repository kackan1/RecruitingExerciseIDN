package org.example.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.example.Person.Person;
import org.example.Person.Person.PersonField;
import org.example.Person.PersonService;

public class ApplicationLoop {
  private static final String EXTERNAL_DIR = "db/external";
  private static final String INTERNAL_DIR = "db/internal";
  private static final Scanner scanner = new Scanner(System.in);
  private static boolean looping = true;
  private static final PersonService personService = new PersonService(EXTERNAL_DIR, INTERNAL_DIR);

  //application loop and terminal ui
  public static void loop() {
    System.out.println("Application working!");
    System.out.println("--------------------");
    while (looping) {
      System.out.println("1: find Employye by id");
      System.out.println("2: create Employee");
      System.out.println("3: delete Employee");
      System.out.println("4: find all Employees");
      System.out.println("5: modify Employee");
      System.out.println("6: find Employees by parameters");
      System.out.println("0: exit app");
      System.out.println("--------------------");
      String userInput = scanner.nextLine();
      switch (userInput) {
        case "1":
          System.out.print("Type id:");
          personService.findById(scanner.nextLine());
          break;
        case "2":
          Person person = new Person();
          System.out.print("external(t/f):");
          String check = scanner.nextLine();
          //assigns first part of id to know which directory to scan to assign rest of id
          String val;
          if (check.equals("t") || check.equals("f")) {
            val = check.equals("t") ? "E" : "I";
          } else {
            System.out.println("wrong input please write 't' or 'f'");
            break;
          }
          person.setPersonId(val);
          System.out.print("firstname:");
          person.setFirstName(scanner.nextLine());
          System.out.print("lastname:");
          person.setLastName(scanner.nextLine());
          System.out.print("mobile:");
          person.setMobile(scanner.nextLine());
          System.out.print("email:");
          person.setEmail(scanner.nextLine());
          System.out.print("pesel:");
          person.setPesel(scanner.nextLine());
          personService.create(person);
          break;
        case "3":
          System.out.println("type id:");
          personService.remove(scanner.nextLine());
          break;
        case "4":
          personService.findAll();
          break;
        case "5":
          Person personToModify = new Person();
          System.out.print("Employee id:");
          personToModify.setPersonId(scanner.nextLine());
          System.out.print("firstname:");
          personToModify.setFirstName(scanner.nextLine());
          System.out.print("lastname:");
          personToModify.setLastName(scanner.nextLine());
          System.out.print("mobile:");
          personToModify.setMobile(scanner.nextLine());
          System.out.print("email:");
          personToModify.setEmail(scanner.nextLine());
          System.out.print("pesel:");
          personToModify.setPesel(scanner.nextLine());
          personService.modify(personToModify);
          break;
        case "6":
          Map<String, String> parameters = new HashMap<>();
          System.out.print("Employee id:");
          parameters.put(PersonField.PERSON_ID.get(), scanner.nextLine());
          System.out.print("firstname:");
          parameters.put(PersonField.FIRST_NAME.get(), scanner.nextLine());
          System.out.print("lastname:");
          parameters.put(PersonField.LAST_NAME.get(), scanner.nextLine());
          System.out.print("mobile:");
          parameters.put(PersonField.MOBILE.get(),scanner.nextLine());
          System.out.print("email:");
          parameters.put(PersonField.EMAIL.get() ,scanner.nextLine());
          System.out.print("pesel:");
          parameters.put(PersonField.PESEL.get() ,scanner.nextLine());
          personService.findByParameter(parameters);
          break;
        case "0":
          looping = false;
          break;
        default:
          System.out.println("wrong input");
          break;
      }
      scanner.reset();
    }
  }

  public void getAllFields(){

  }
}
