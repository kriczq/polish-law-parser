package project;

import project.input.InputParser;
import project.input.FileProcessor;
import project.model.ContentParser;
import project.model.ContentType;
import project.view.Printer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        try {
            InputParser input = new InputParser(args);

            if (input.getMode() == InputParser.Mode.Help) {
                System.out.println("usage: java -jar parser.jar [filename] [-t] [parameters]\n" +
                "parameters can be: dzial ... rozdzial ... art ... ust ... pkt ... lit ...\n" +
                "use -t if you wanna show tables of contents but after can be only dzial ...\n" +
                        "ex. java -jar parser.jar uokik.txt art 113f ust 2 pkt 1");
                return;
            }

            FileProcessor file = new FileProcessor(input.getFilename());
            ContentParser parser = new ContentParser(file.getContent());

            switch (input.getMode()) {
                case Table:
                    if (input.getId().containsKey(ContentType.Dział)) {
                        Printer.printTable(parser.getRoot().getChild(input.getId().get(ContentType.Dział)));
                    } else {
                        Printer.printTable(parser.getRoot());
                    }
                    break;
                case Range:
                    Printer printer = new Printer(parser.getRoot());
                    printer.printRange(input.getRange().getFrom(), input.getRange().getTo());
                    break;
                case Element:
                    Printer printer1 = new Printer(parser.getRoot());
                    printer1.printElement(input.getId());
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}