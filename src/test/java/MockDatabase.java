import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MockDatabase {
  private final String DB_PATH;
  private final String EXTERNAL;
  private final String INTERNAL;

  public MockDatabase(String dbpath, String external, String internal) {
    this.DB_PATH = dbpath;
    this.EXTERNAL = external;
    this.INTERNAL = internal;
  }

  public void mock(){
    File dir = new File(DB_PATH);
    if (dir.mkdir()) {
      System.out.println("Created db folder for testing.");
    }
    dir = new File(EXTERNAL);
    if (dir.mkdir()) {
      System.out.println("Created db/external folder for testing.");
    }
    dir = new File(INTERNAL);
    if (dir.mkdir()) {
      System.out.println("Created db/internal folder for testing.");
    }

  }

  public void removeMockedDB(){
    try {
      Files.walkFileTree(Path.of(DB_PATH), new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          Files.delete(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          Files.delete(dir);
          return FileVisitResult.CONTINUE;
        }
      });

      System.out.println("Mocked DB removed successfully.");
    } catch (IOException e) {
      System.err.println("Failed to delete directory: " + e.getMessage());
    }
  }
}
