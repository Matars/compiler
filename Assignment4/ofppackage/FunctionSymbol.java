package ofppackage;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class FunctionSymbol extends Symbol {
    private ArrayList<Symbol> parameters = new ArrayList<>();
    public Map<Symbol, Integer> indices = new LinkedHashMap<Symbol, Integer>();
    private int index = 1;

    public FunctionSymbol(OFPType type, String name) {
        super(type, name);
        this.parameters = new ArrayList<>();
    }

    public void addParameter(Symbol param) {
        parameters.add(param);
    }

    public void addVariable(Symbol var) {
        indices.put(var, index++);
    }

    public int getParameterCount() {
        return parameters.size();
    }

    public ArrayList<Symbol> getParameters() {
        return parameters;
    }

    public Symbol getParameter(int index) {
        return parameters.get(index);
    }

    public int indexOf(Symbol sym) {
        return indices.get(sym);
    }

}