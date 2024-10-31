package ofppackage;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class FunctionSymbol extends Symbol {
    private ArrayList<Symbol> parameters = new ArrayList<>();
    public Map<Symbol, Integer> indices = new LinkedHashMap<Symbol, Integer>();
    private int localVarIndex;

    public FunctionSymbol(OFPType type, String name) {
        super(type, name);
        this.parameters = new ArrayList<>();
        this.localVarIndex = 1;
    }

    public void addParameter(Symbol param) {
        // Parameters get indices 0, 1, etc.
        indices.put(param, parameters.size());
        parameters.add(param);
    }

    public void addVariable(Symbol var) {
        // add if not already a parameter
        if (!parameters.contains(var)) {
            // if float, increment index by 2
            if (var.getType().toString().equals("float")) {
                localVarIndex += 2;
            } else {
                localVarIndex++;
            }
            indices.put(var, localVarIndex);
        }
    }

    public int indexOf(Symbol sym) {
        // For parameters, calculate correct index
        if (parameters.contains(sym)) {
            return parameters.indexOf(sym);
        }
        return indices.get(sym);
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
}