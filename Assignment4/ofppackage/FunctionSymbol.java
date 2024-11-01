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
        parameters.add(param);
        addVariable(param);

    }

    public int indexOf(Symbol sym) {
        // For parameters, get index from parameters list
        if (parameters.contains(sym)) {
            return parameters.indexOf(sym);
        }
        // For local variables, get index from indices map
        Integer index = indices.get(sym);
        if (index == null) {
            throw new RuntimeException("Symbol not found: " + sym.getName());
        }
        return index;
    }

    public void addVariable(Symbol var) {
        // Only add to indices if not already a parameter
        if (!parameters.contains(var)) {
            // Store current index
            indices.put(var, localVarIndex);

            // Increment index based on type
            if (var.getType().toString().equals("float")) {
                localVarIndex += 2;
            } else {
                localVarIndex++;
            }
        }
    }

    public ArrayList<Symbol> getParameters() {
        return parameters;
    }

    public int getParameterCount() {
        return parameters.size();
    }

    public Symbol getParameter(int index) {
        return parameters.get(index);
    }
}