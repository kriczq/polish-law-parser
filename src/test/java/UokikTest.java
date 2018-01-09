import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.input.FileProcessor;
import project.model.ContentParser;
import project.model.ContentType;
import project.view.Printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UokikTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void test() throws IOException {
        Map<ContentType, String> id = new EnumMap<ContentType, String>(ContentType.class);
        id.put(ContentType.Art, "113f");
        id.put(ContentType.Ustep, "2");
        id.put(ContentType.Punkt, "2");

        FileProcessor file = new FileProcessor("uokik.txt");

        ContentParser parser = new ContentParser(file.getContent());

        Printer printer = new Printer(parser.getRoot());
        printer.printElement(id);

        assertEquals("2) produktów lub usług, których dotyczy porozumienie;\n", outContent.toString());
    }
}
