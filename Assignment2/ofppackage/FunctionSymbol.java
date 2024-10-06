package ofppackage;

import java.util.List;
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
    private List<Symbol> parameters = new ArrayList<>();

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

    public List<OFPType> getParameterTypes() {

        List<OFPType> types = new ArrayList<>();
        for (Symbol param : parameters) {
            types.add(param.getType());
        }
        return types;
    }

    public Symbol getParameter(int index) {
        return parameters.get(index);
    }

}