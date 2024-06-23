package Tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.Person.Person;
import org.example.Person.PersonService;
import org.example.Utils.XMLHandler;

public class PersonServiceTest {
  private PersonService personService;
  private String external;
  private String internal;
  private MockDatabase mockDatabase;
  private Map<String, String> testingResults;

  public PersonServiceTest(String external, String internal, MockDatabase mockDatabase) {
    personService = new PersonService(external, internal);
    this.external = external;
    this.internal = internal;
    this.mockDatabase = mockDatabase;
    this.testingResults = new HashMap<>();
  }

  public void beginTest(){
    testCreateEmployeeWithAllFields_And_NoOtherExistingRecords();
    testCreateEmployeeWithNotAllFields_And_ExistingRecords();
    testRemoveEmployeeWithGivenId_And_EmployeeExists();
    testRemoveEmployeeWithGivenId_And_EmployeeDoesNotExist();
    testModifyEmployeeWithGivenId_And_CompletePersonObject();
    testModifyEmployeeWithNoGivenId_And_CompletePersonObject();
  }

  public void testResults(){
    testingResults.forEach((k,v) -> {
      System.out.println(k + " --- " + v);
    });
  }

  //Should create a files named E001.xml and I001.xml and file count should be 2
  public void testCreateEmployeeWithAllFields_And_NoOtherExistingRecords(){
    //given
    mockDatabase.initialize();
    Person internalPerson = new Person("I", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928");
    Person externalPerson = new Person("E", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928");
    //when
    personService.create(internalPerson);
    personService.create(externalPerson);
    //then
    if (
        personService.findAll().size()==2
        && Files.exists(Path.of(external + "/E001.xml"))
        && Files.exists(Path.of(internal + "/I001.xml"))
    ){
      testingResults.put("testCreateEmployeeWithAllFields_And_NoOtherExistingRecords", "passed");
    } else {
      testingResults.put("testCreateEmployeeWithAllFields_And_NoOtherExistingRecords", "failed");
    }
    //cleanup after testing
    mockDatabase.removeMockedDB();
  }

  //Should create a files named E002.xml and I002.xml
  public void testCreateEmployeeWithNotAllFields_And_ExistingRecords(){
    //given
    mockDatabase.initialize();
    personService.create(new Person("I", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
    personService.create(new Person("E", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
    Person internalPerson = new Person("I", "test", "", "", "asdkfjjk@ask.sl", "123994928");
    Person externalPerson = new Person("E","", "test", "12309921", "@ask.sl", "2131412352");
    //when
    personService.create(internalPerson);
    personService.create(externalPerson);
    //then
    if (
        Files.exists(Path.of(external + "/E002.xml"))
        && Files.exists(Path.of(internal + "/I002.xml"))
    ){
      testingResults.put("testCreateEmployeeWithNotAllFields_And_ExistingRecords", "passed");
    } else {
      testingResults.put("testCreateEmployeeWithNotAllFields_And_ExistingRecords", "failed");
    }
    //cleanup after testing
    mockDatabase.removeMockedDB();
  }

  //Should remove a file E002.xml and I001.xml when E002 and I001 is passed as id to remove method
  public void testRemoveEmployeeWithGivenId_And_EmployeeExists(){
    //given
    mockDatabase.initialize();
    addPersonsToDb();
    //when
    personService.remove("E002");
    personService.remove("I001");
    //then
    if (Files.notExists(Path.of(external + "/E002.xml")) && Files.notExists(Path.of(internal + "/I001.xml"))){
      testingResults.put("testRemoveEmployeeWithGivenId_And_EmployeeExists", "passed");
    } else {
      testingResults.put("testRemoveEmployeeWithGivenId_And_EmployeeExists", "failed");
    }
    //cleanup after testing
    mockDatabase.removeMockedDB();
  }

  //Should inform that records does not exist and return false
  public void testRemoveEmployeeWithGivenId_And_EmployeeDoesNotExist(){
    //given
    mockDatabase.initialize();
    addPersonsToDb();
    //when
    boolean removingE = personService.remove("E099");
    boolean removingI = personService.remove("I099");
    //then
    if (!removingE && !removingI) {
      testingResults.put("testRemoveEmployeeWithGivenId_And_EmployeeDoesNotExist", "passed");
    } else {
      testingResults.put("testRemoveEmployeeWithGivenId_And_EmployeeDoesNotExist", "failed");
    }
    //cleanup after testing
    mockDatabase.removeMockedDB();
  }

  //should return true if modyfing is successfull
  public void testModifyEmployeeWithGivenId_And_CompletePersonObject(){
    //given
    mockDatabase.initialize();
    addPersonsToDb();
    Person personWithChanges = new Person("E001", "Modify", "Modify", "Modify", "Modify", "Modify");
    //when
    boolean modifyResult = personService.modify(personWithChanges);
    //then
    Person modifiedPerson = XMLHandler.parseFile(new File(external + "/E001.xml"));
    if (modifiedPerson != null) {
      if (modifyResult && compareTwoPersons(personWithChanges, modifiedPerson)) {
        testingResults.put("testModifyEmployeeWithGivenId_And_CompletePersonObject", "passed");
      } else {
        testingResults.put("testModifyEmployeeWithGivenId_And_CompletePersonObject", "failed");
      }
    }
    //cleanup after testing
    mockDatabase.removeMockedDB();
  }

  //should return false when modyfing and no id is provided
  public void testModifyEmployeeWithNoGivenId_And_CompletePersonObject(){
    //given
    mockDatabase.initialize();
    addPersonsToDb();
    Person personWithChanges = new Person("Modify", "Modify", "Modify", "Modify", "Modify");
    //when
    boolean modifyResult = personService.modify(personWithChanges);
    //then
    if (!modifyResult) {
      testingResults.put("testModifyEmployeeWithNoGivenId_And_CompletePersonObject", "passed");
    } else {
      testingResults.put("testModifyEmployeeWithNoGivenId_And_CompletePersonObject", "failed");
    }
    //cleanup after testing
    mockDatabase.removeMockedDB();
  }


  //adds 3 employees to external and 3 to internal
  public void addPersonsToDb(){
    personService.create(new Person("I", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
    personService.create(new Person("I", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
    personService.create(new Person( "I", "John", "Brown", "56789012", "john.brown@example.com", "345678901"));
    personService.create(new Person("E", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
    personService.create(new Person("E", "Michael", "Johnson", "78901234", "m.johnson@example.com", "567890123"));
    personService.create(new Person("E", "John", "Doe", "12309921", "asdkfjjk@ask.sl", "123994928"));
  }
  public boolean compareTwoPersons(Person person1, Person person2){
    boolean result = true;
    if (!person1.getPersonId().equals(person2.getPersonId())) result = false;
    if (!person1.getFirstName().equals(person2.getFirstName())) result = false;
    if (!person1.getLastName().equals(person2.getLastName())) result = false;
    if (!person1.getEmail().equals(person2.getEmail())) result = false;
    if (!person1.getMobile().equals(person2.getMobile())) result = false;
    if (!person1.getPesel().equals(person2.getPesel())) result = false;
    return result;
  }
}
