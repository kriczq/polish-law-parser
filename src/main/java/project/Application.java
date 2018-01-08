package project;

import project.input.InputParser;
import project.input.FileProcessor;
import project.model.Content;
import project.model.ContentParser;
import project.model.ContentType;
import project.view.Printer;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        try {
            //InputParser parser = new InputParser("Art. 2, ust. 2., pkt 2), lit. a)");


            //chapter->article->section->clause->

            InputParser input = new InputParser(args);
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
                    break;
            }

            /* test 1
            Map<ContentType, String> id = new EnumMap<ContentType, String>(ContentType.class);
            id.put(ContentType.Art, "113f");
            id.put(ContentType.Ustep, "2");
            id.put(ContentType.Punkt, "2");

            printer.printElement(id);
            */

            //printer.printRange("113a", "113e");

            //System.out.println(parser.getRoot().find("113a", ContentType.Art));


            //parser.getRoot().getList(ContentType.Art).forEach(x -> System.out.println(x + "\n--------"));



            //Printer.printTable(parser.getRoot());
            //System.out.println(parser.getRoot().getChild("V").getChild("127").getChild("3").getText());
            /*
            file.show();

            Act act = new Act(file.getContent());
            act.showChildren();*/
        } catch (IOException ex) {
            System.out.println(ex.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.toString());
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

        /*public static void main(String args[])
        {
            System.out.println("\033[31;1mHello\033[0m, \033[32;1;2mworld!\033[0m");
            System.out.println("\033[31mRed\033[32m, Green\033[33m, Yellow\033[34m, Blue\033[0m");
        }*/
}