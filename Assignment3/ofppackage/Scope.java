package ofppackage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Scope {
    private Scope enclosingScope = null; // null if global (outermost) scope
    private Map<String, Symbol> symbols = new LinkedHashMap<String, Symbol>();
    public String name;

    public Scope(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public boolean addSymbol(Symbol symbol) {
        if (symbols.containsKey(symbol.getName())) {
            return false; // Duplicate declaration in the same scope
        }
        symbols.put(symbol.getName(), symbol);
        return true;
    }

    public Symbol resolveLocally(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        }
        return null;
    }

    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        }

        if (enclosingScope != null) {
            return enclosingScope.resolve(name);
        }

        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void ToString() {
        System.out.println("current scope: " + name);
        for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
            System.out.println("\t" + entry.getValue().toString());
        }
    }

}
