import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Parser {
    public static Scanner scanner;

    // Stack-based symbol table to track declared variables per scope
    public static Stack<HashSet<String>> scopeStack = new Stack<>();

    // Map to track variable types (true = object, false = integer)
    private static HashMap<String, Boolean> variableTypes = new HashMap<>();

    static void expectedToken(Core expected) {
        if (scanner.currentToken() != expected) {
            System.out.println("ERROR: Expected " + expected + ", received " + scanner.currentToken());
            System.exit(1);
        }
    }

    // Push a new scope onto the stack
    public static void enterScope() {
        scopeStack.push(new HashSet<>());
    }

    // Pop the current scope from the stack and remove its variables
    public static void exitScope() {
        if (!scopeStack.isEmpty()) {
            HashSet<String> removedScope = scopeStack.pop();
            for (String var : removedScope) {
                variableTypes.remove(var); // Ensure variables are removed from tracking
            }
        }
    }

    // Declare a variable in the current scope and track its type
    public static void declareVariable(String varName, boolean isObject) {
        if (scopeStack.isEmpty()) {
            // If no scope exists, create a global scope
            scopeStack.push(new HashSet<>());
        }
        HashSet<String> currentScope = scopeStack.peek();
        if (currentScope.contains(varName)) {
            System.out.println("ERROR: Variable '" + varName + "' is already declared in this scope.");
            System.exit(1);
        }
        currentScope.add(varName);
        variableTypes.put(varName, isObject);
    }

    // Check if a variable is declared in the **current** or **parent scopes**
    public static boolean isVariableDeclared(String varName) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            if (scopeStack.get(i).contains(varName)) {
                return true;
            }
        }
        return false;
    }

    // Check if a variable is an object
    public static boolean isObject(String varName) {
        if (!isVariableDeclared(varName)) {
            System.out.println("ERROR: Variable '" + varName + "' used before declaration.");
            System.exit(1);
        }
        return variableTypes.getOrDefault(varName, false); // Default to false (integer) if unknown
    }

    // **Fix: Ensure no extra tokens exist after 'END'**
    public static void checkForExtraTokens() {
        if (scanner.currentToken() != Core.EOS) {
            System.out.println("ERROR: Extra tokens after end.");
            System.exit(1);
        }
    }
}
