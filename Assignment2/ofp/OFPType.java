package ofp;

public class OFPType {

    private String name;

    public static final OFPType voidType = new OFPType("void");
    public static final OFPType intType = new OFPType("int");
    public static final OFPType floatType = new OFPType("float");
    public static final OFPType boolType = new OFPType("bool");
    public static final OFPType charType = new OFPType("char");
    public static final OFPType stringType = new OFPType("string");
    public static final OFPType intArrayType = new OFPType("int[]");
    public static final OFPType floatArrayType = new OFPType("float[]");
    public static final OFPType charArrayType = new OFPType("char[]");

    public OFPType(String name) {
        this.name = name;
    }

    public static OFPType getTypeFor(String typeName) {
        switch (typeName) {
            case "void":
                return voidType;
            case "int":
                return intType;
            case "float":
                return floatType;
            case "bool":
                return boolType;
            case "char":
                return charType;
            case "string":
                return stringType;
            case "int[]":
                return intArrayType;
            case "float[]":
                return floatArrayType;
            case "char[]":
                return charArrayType;
            default:
                return null;
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "OFPType: " + this.name;
    }
}