package project.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentParser {
    private final Content root;

    public ContentParser(String text) {
        root = new Content(text, ContentType.Ustawa);

        parse(root, root.getType().next());
    }

    private void parse(Content node, ContentType type) {
        if (type.getPattern() == null)
            return;

        Matcher m = Pattern.compile(type.getPattern()).matcher(node.toString());

        while (m.find()) {
            Content child = new Content(m.group(1), type);
            child.setIdentifier(m.group(2));

            if (m.groupCount() == 3)
                child.setTitle(m.group(3));

            node.addChild(child);

            parse(child, child.getType().next());
        }

        if (!node.hasChildren())
        {
            parse(node, type.next());
        }
    }

    public Content getRoot() {
        return root;
    }
}
