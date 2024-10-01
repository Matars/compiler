package ofp;

import java.util.List;
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
    private List<Symbol> parameters;

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

    public List<Symbol> getParameters() {
        return parameters;
    }
}