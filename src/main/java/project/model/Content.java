package project.model;

import java.util.*;

public class Content {
    private final String text;
    private final ContentType type;

    private String identifier;
    private String title;

    private Map<String, Content> children = new LinkedHashMap<>();


    public Content(String text, ContentType type) {
        this.text = text;
        this.type = type;
    }

    public void addChild(Content child) {
        children.put(child.getIdentifier(), child);
    }

    public Content getChild(String identifier) {
        return children.get(identifier);
    }

    public Map<String, Content> getChildren() {
        return children;
    }

    public ContentType getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public List<Content> getList(ContentType type) {
        if (getType() != type) {
            List<Content> l = new ArrayList<>();
            getChildren().forEach((u, v) -> l.addAll(v.getList(type)));
            return l;
        } else {
            return Arrays.asList(this);
        }
    }

    public Content find(String identifier, ContentType type) {
        if (this.identifier != null && this.identifier.equals(identifier) && this.type == type)
            return this;

        for (Content x : children.values())
        {
            Content result = x.find(identifier, type);
            if (result != null)
                return result;
        }

        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
