package Tests;

import org.example.Person.PersonService;

public class PersonServiceTest {
  PersonService personService;

  public PersonServiceTest(String external, String internal) {
    personService = new PersonService(external, internal);
  }

  public void beginTest(){

  }

  //Should create a files named E001.xml and I001.xml
  public void testCreateEmployeeWithAllFields_And_NoOtherExistingRecords(){

  }

}
