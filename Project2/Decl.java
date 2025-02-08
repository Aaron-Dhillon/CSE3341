public class Decl {
    String id;
    boolean isInteger; // Determines if it's an integer or object declaration

    void parse() {
        if (Parser.scanner.currentToken() == Core.INTEGER) {
            isInteger = true;
        } else if (Parser.scanner.currentToken() == Core.OBJECT) {
            isInteger = false;
        } else {
            System.out.println("ERROR: Expected 'integer' or 'object' in declaration. Got: " + Parser.scanner.currentToken());
            System.exit(1);
        }

        Parser.scanner.nextToken(); // Move past 'integer' or 'object'
        
        // Expect an identifier (ID)
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();

        // Ensure the variable is not already declared in the current scope
        if (Parser.isVariableDeclared(id)) {
            System.out.println("ERROR: Variable '" + id + "' is already declared in this scope.");
            System.exit(1);
        }

        // Register the variable in the current scope with its type
        Parser.declareVariable(id, !isInteger); // True if object, false if integer

        Parser.scanner.nextToken();

        // Expect a semicolon ';'
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print() {
        System.out.println((isInteger ? "integer " : "object ") + id + ";");
    }
}
