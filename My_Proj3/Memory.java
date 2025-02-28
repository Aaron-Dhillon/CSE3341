import java.util.*;

class Memory {
    private Map<String, Integer> globalMemory;
    private Stack<Map<String, Integer>> localMemory;

    public Memory() {
        globalMemory = new HashMap<>();
        localMemory = new Stack<>();
    }

    // Enter a new local scope
    public void newScope() {
        localMemory.push(new HashMap<>());
    }

    // Exit the current local scope
    public void exitCurrentScope() {
        if (!localMemory.isEmpty()) {
            localMemory.pop();
        } else {
            System.out.println("ERROR: No local scope to exit");
            System.exit(1);
        }
    }

    public void setVariable(String varName, int value) {
        if (!localMemory.isEmpty() && localMemory.peek().containsKey(varName)) {
            localMemory.peek().put(varName, value);
        } else if (globalMemory.containsKey(varName)) {
            globalMemory.put(varName, value);
        } else {
            System.out.println("ERROR: Undeclared variable '" + varName + "'");
            System.exit(1);
        }
    }

    public int getVariable(String varName) {
        if (!localMemory.isEmpty() && localMemory.peek().containsKey(varName)) {
            return localMemory.peek().get(varName);
        } else if (globalMemory.containsKey(varName)) {
            return globalMemory.get(varName);
        } else {
            System.out.println("ERROR: Undeclared variable '" + varName + "'");
            System.exit(1);
            return 0;
        }
    }

    // Declare a new variable (local if inside a scope, global otherwise)
    public void declareVariable(String varName) {
        if (!localMemory.isEmpty()) {
            localMemory.peek().put(varName, 0);  // Local variable with default value 0
        } else {
            globalMemory.put(varName, 0);  // Global variable
        }
    }
}
