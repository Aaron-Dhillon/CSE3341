import java.util.*;

class Memory {
    private Map<String, Variable> globalMemory;
    private Stack<Map<String, Variable>> localMemoryStack;
    private Scanner dataScanner;

    public Memory(Scanner dataScanner) {
        globalMemory = new HashMap<>();
        localMemoryStack = new Stack<>();
        this.dataScanner = dataScanner;
    }

    public int readInput() {
        if(dataScanner.currentToken() == Core.EOS){
            System.out.println("ERROR: No more input to read.");
            System.exit(1);
        }
        int value = dataScanner.getConst();
        dataScanner.nextToken();
        return value;
    }

    // Enter a new local scope
    public void enterScope() {
        localMemoryStack.push(new HashMap<>());
    }

    // Exit the current local scope
    public void exitScope() {
        if (!localMemoryStack.isEmpty()) {
            localMemoryStack.pop();
        } else {
            System.out.println("ERROR: Attempted to exit global scope.");
            System.exit(1);
        }
    }

    // Declare a variable (can be integer or object)
    public void declareVariable(String varName, boolean isInteger) {
        getCurrentScope().put(varName, new Variable(isInteger));
    }

    // Assign an integer value to a variable
    public void setIntVariable(String varName, int value) {
        getVariable(varName).setIntValue(value);
    }

    // Retrieve an integer value
    public int getIntVariableValue(String varName) {
        return getVariable(varName).getIntValue();
    }

    // Create a new object with a default key-value pair
    public void createObject(String varName, String key, int value) {
        getVariable(varName).initializeAsObject(key, value);
    }

    // Set an object key-value pair
    public void setObjectKey(String varName, String key, int value) {
        if(getVariable(varName).objectValue == null){
            getVariable(varName).initializeAsObject(key, value);
        }else{
            getVariable(varName).setObjectKey(key, value);
        }
    }

    // Get an object's key-value pair
    public int getObjectKey(String varName, String key) {
        return getVariable(varName).getObjectKey(key);
    }

    // Aliasing: Make var1 reference the same object as var2
    public void aliasObject(String var1, String var2) {
        getVariable(var1).aliasTo(getVariable(var2));
    }

    // Helper: Retrieve a variable from the correct scope
    private Variable getVariable(String varName) {
        for (int i = localMemoryStack.size() - 1; i >= 0; i--) {
            if (localMemoryStack.get(i).containsKey(varName)) {
                return localMemoryStack.get(i).get(varName); // Return from correct local scope
            }
        }
        if (globalMemory.containsKey(varName)) {
            return globalMemory.get(varName);
        }
        System.out.println("ERROR: Undeclared variable '" + varName + "'");
        System.exit(1);
        return null;
    }
    // Check if a variable exists in either local or global scope
public boolean variableExists(String varName) {
    // Check local scopes first (from most recent to oldest)
    for (int i = localMemoryStack.size() - 1; i >= 0; i--) {
        if (localMemoryStack.get(i).containsKey(varName)) {
            return true; // Found in local scope
        }
    }
    // Check global scope
    return globalMemory.containsKey(varName);
}

    


    // Helper: Get the current scope (local or global)
    private Map<String, Variable> getCurrentScope() {
        return localMemoryStack.isEmpty() ? globalMemory : localMemoryStack.peek();
    }
}
