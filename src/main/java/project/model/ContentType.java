package project.model;

public enum ContentType {
    Ustawa(null),
    Dział("(DZIAŁ (.+)\\n(.+)\\n(?:(?!\\nDZIAŁ .+\\n)[\\s\\S])*)"),
    Rodział("(Rozdział (.+)\\n(.+)\\n(?:(?!\\nRozdział .+\\n)[\\s\\S])*)"),
    Art("(Art\\. ([0-9]+[a-z]*)\\.(?:(?!\\nArt\\. [0-9]+[a-z]*\\.)[\\s\\S])*)"),
    Ustep("(?:Art\\. [0-9]+\\. |\\n)(([0-9]+)\\. (?:(?!\\n[0-9]+\\. )[\\s\\S])*)"),
    Punkt("\\n(([0-9]+[a-z]?)\\) (?:(?!\\n[0-9]+[a-z]?\\) )[\\s\\S])*)"),
    Litera("\\n(([a-z]?)\\) (?:(?!\\n[a-z]?\\) )[\\s\\S])*)");

    private final String pattern;
    private static ContentType[] vals = values();

    ContentType(String pattern) {
        this.pattern = pattern;
    }

    public ContentType next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

    public boolean hasNext()
    {
        return this.ordinal() != vals.length - 1;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return this.name();
    }
}