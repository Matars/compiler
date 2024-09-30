package ofp;

public class Symbol {
    protected String name;
    protected String type;

    public Symbol(String type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return type + ": " + name;
    }
}
