package project.view;

import project.model.Content;
import project.model.ContentType;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Printer {
    private final Content root;

    public Printer(Content root) {
        this.root = root;
    }

    public static void printTable(Content root) {
        root.getChildren().forEach((u, v) -> {
            if (v.getTitle() != null) {
                System.out.println(u + " " + v.getTitle());
                printTable(v);
            }
        });
    }

    public void printElement(Map<ContentType, String> element) {
        Content c = root;

        for (Map.Entry<ContentType, String> entry : element.entrySet()) {
            //System.out.println("szukam " + entry.getValue() + " " + entry.getKey());
            c = c.find(entry.getValue(), entry.getKey());
        }

        System.out.println(c);
    }


    public void printRange(String first, String last) {
        List<Content> arts = root.getList(ContentType.Art);

        OptionalInt start = IntStream.range(0, arts.size())
                .filter(x -> first.equals(arts.get(x).getIdentifier()))
                .findFirst();

        OptionalInt end = IntStream.range(0, arts.size())
                .filter(x -> last.equals(arts.get(x).getIdentifier()))
                .findFirst();

        start.orElseThrow(() -> new NullPointerException("art. " + first + " not found"));
        end.orElseThrow(() -> new NullPointerException("art. " + last + " not found"));

        if (start.getAsInt() > end.getAsInt()) throw new NullPointerException("empty przedzial");

        arts.subList(start.getAsInt(), end.getAsInt()+1).forEach(System.out::println);
    }
}
