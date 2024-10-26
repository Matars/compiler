package ofppackage;

public class Symbol {
    protected String name;
    protected OFPType type;

    public Symbol(OFPType type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public OFPType getType() {
        return type;
    }

    public String toString() {
        return type.toString() + ": " + name;
    }
}
