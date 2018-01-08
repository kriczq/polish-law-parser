package project.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcessor {
    private String content;

    public FileProcessor(String filename) throws IOException {
        content = new String(getClass().getClassLoader().getResourceAsStream(filename).readAllBytes());
        content = format(content);
    }

     private String format(String content) {
         return content.replaceAll("(\\p{L})-\n([\\p{L},\\.]+)\\s*", "$1$2\n").
                 replaceAll("©Kancelaria Sejmu.*\n\\d{4}-\\d{2}-\\d{2}\n", "");
    }

    public void show() {
        System.out.println(content);
    }

    public String getContent() {
        return content;
    }
}
