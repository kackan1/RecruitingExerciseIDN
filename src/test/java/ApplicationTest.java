import Tests.PersonServiceTest;
import org.example.Person.PersonService;

public class ApplicationTest {
  private static final String DB_PATH = "src/test/java/db";
  private static final String EXTERNAL = DB_PATH + "/external";
  private static final String INTERNAL = DB_PATH + "/internal";

  public static void main(String[] args) {
    MockDatabase mockDatabase = new MockDatabase(DB_PATH, EXTERNAL, INTERNAL);
    mockDatabase.mock();
    PersonServiceTest personServiceTest = new PersonServiceTest(EXTERNAL, INTERNAL);







    mockDatabase.removeMockedDB();
  }
}
