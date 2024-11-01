import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.nio.file.Files.readString;
import static org.junit.jupiter.api.Assertions.*;
import org.AssignemntOS.CLI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CLITest {

    private CLI cli;
    private Path testDir;







    //---------------------------------------------------------
    //---------------------------------------------------------

    @Test
    void testLs(){
        cli.touch("File1.txt");
        cli.touch("File2.txt");
        cli.mkdir("Gemmy");
        cli.touch("File3.txt");

        String expected = "File1.txt\nFile2.txt\nFile3.txt\nGemmy";
        String currLs = String.join("\n", cli.ls());
        assertEquals(expected.trim() , currLs, "ls should print all file and directories.");
    }

    //---------------------------------------------------------
    //---------------------------------------------------------

    @Test
    void testMv() {
        cli.touch("File1.txt");
        cli.touch("File2.txt");
        cli.touch("File3.txt");
        cli.mkdir("dirc");

        // move
        cli.mv("File1.txt dirc/File1.txt");
        assertTrue(Files.exists(testDir.resolve("dirc/File1.txt")), "File.txt should move to dirc");
        assertFalse(Files.exists(testDir.resolve("file1.txt")), "file1.txt should not move to dirc");

        // rename
        cli.mv("File3.txt mohamed.txt");
        assertTrue(Files.exists(testDir.resolve("mohamed.txt")), "file3.txt should be renamed to mohamed.txt.");


        // Move multiple files to the dir
        cli.touch("test1.txt");
        cli.touch("test2.txt");
        cli.touch("test3.txt");

        cli.mv("test1.txt test2.txt test3.txt dirc");

        assertTrue(Files.exists(testDir.resolve("dirc/test1.txt")), "test1.txt should move to dirc");
        assertTrue(Files.exists(testDir.resolve("dirc/test2.txt")), "test2.txt should move to dirc");
        assertTrue(Files.exists(testDir.resolve("dirc/test3.txt")), "test3.txt should move to dirc");
    }

    //---------------------------------------------------------
    //---------------------------------------------------------

    @Test
    void redirectTest() throws IOException {
        String fileName = "file3.txt";
        Path filePath = testDir.resolve(fileName);

        cli.touch(fileName);

        String expectedContent = "Welcome to Command Line Interpreter";
        cli.redirect(expectedContent, filePath.toString(), false);

        assertTrue(Files.exists(filePath), "The file should exist after redirecting content.");

        String writtenContent = Files.readString(filePath).stripTrailing();

        assertEquals(expectedContent, writtenContent, "The content should match after redirecting.");
    }


    @Test
    void appendTest() throws IOException {
        cli.touch("file3.txt");
        Path filePath = testDir.resolve("file3.txt");

        assertTrue(Files.exists(filePath), "The file should exist after creating.");

        String initialContent = "Welcome to Command Line Interpreter";
        cli.redirect(initialContent, filePath.toString(), false); // write

        String appendedContent = "This is an appended line.";
        cli.redirect(appendedContent, filePath.toString(), true); // Append content

        String forTestWritten = Files.readString(filePath).stripTrailing();

        assertEquals(initialContent + "\n" + appendedContent , forTestWritten, "The content should match after appending.");
    }


    //---------------------------------------------------------
    //---------------------------------------------------------


}