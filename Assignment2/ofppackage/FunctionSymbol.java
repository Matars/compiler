package ofppackage;

import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
    private ArrayList<Symbol> parameters = new ArrayList<>();

    public FunctionSymbol(OFPType type, String name) {
        super(type, name);
        this.parameters = new ArrayList<>();
    }

    public void addParameter(Symbol param) {
        parameters.add(param);
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