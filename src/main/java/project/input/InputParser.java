package project.input;

import project.model.ContentType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    dział
      |
      v
   rodział
      |
      V
     art
      |
      V
    ustęp
      |
      V
    punkt
      |
      V
    litera
 */

public class InputParser {
    public enum Mode {Table, Range, Element, Help}

    private String input;
    private String filename;
    private Mode mode;
    private ArticleRange range;

    private final Map<ContentType, String> id = new EnumMap<>(ContentType.class);

    public class ArticleRange {
        private final String from, to;

        ArticleRange(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }

    public InputParser(String[] args) {
        if (args.length == 0) {
            mode = Mode.Help;
            return;
        }

        filename = args[0];

        // todo if just file name
        input = String.join(" ", Arrays.copyOfRange(args, args[1].equals("-t") ? 2 : 1, args.length));

        Matcher m;

        if (args[1].equals("-t")) {
            mode = Mode.Table;

            m = Pattern.compile("(?i)^dzial ([A-Z]+)$").matcher(this.input);

            if (m.find())
                id.put(ContentType.Dział, m.group(1));
            else if (!input.isEmpty())
                throw new IllegalArgumentException("only \"dzial\" can be in table mode");

        } else if ((m = Pattern.compile("art\\.? ([0-9a-z]+)-([0-9a-z]+)").matcher(input)).find()) {
            mode = Mode.Range;

            range = new ArticleRange(m.group(1), m.group(2));
        } else if ((m = Pattern.compile("(?i)(?:dzial ([A-Z]+))?\\s*(?:rozdz ([0-9A-Z]+))?\\s*(?:art\\.? [0-9a-z]+\\s*(?:ust\\.? [0-9a-z]+\\s*(?:pkt\\.? [0-9a-z]+\\s*(?:lit\\.? [0-9a-z]+)?)?)?)?").matcher(this.input)).find()) {
            mode = Mode.Element;

            match("dzial ([A-Z]+)").ifPresent(x -> id.put(ContentType.Dział, x));
            match("rozdz ([0-9A-Z]+)").ifPresent(x -> id.put(ContentType.Rodział, x));
            match("art\\.? ([0-9a-z]+)").ifPresent(x -> id.put(ContentType.Art, x));
            match("ust\\.? ([0-9a-z]+)").ifPresent(x -> id.put(ContentType.Ustep, x));
            match("pkt\\.? ([0-9a-z]+)").ifPresent(x -> id.put(ContentType.Punkt, x));
            match("lit\\.? ([0-9a-z]+)").ifPresent(x -> id.put(ContentType.Litera, x));
        } else {
            throw new IllegalArgumentException("bad");
        }
    }

    public Map<ContentType, String> getId() {
        return id;
    }

    public ArticleRange getRange() {
        return range;
    }

    public Mode getMode() {
        return mode;
    }

    public String getFilename() {
        return filename;
    }

    private Optional<String> match(String pattern) {
        Matcher m = Pattern.compile("(?i)" + pattern).matcher(input);

        return m.find() ? Optional.of(m.group(1)) : Optional.empty();
    }
}
