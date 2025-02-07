public class Decl {
    String id;
    boolean isInteger; // Determines whether it's an integer or object declaration

    void parse() {
        // Expect 'integer' or 'object'
        if (Parser.scanner.currentToken() == Core.INTEGER) {
            isInteger = true;
        } else if (Parser.scanner.currentToken() == Core.OBJECT) {
            isInteger = false;
        } else {
            System.out.println("ERROR: Expected 'integer' or 'object' in declaration.");
            System.exit(1);
        }
        Parser.scanner.nextToken(); // Consume 'integer' or 'object'

        // Expect ID
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        // Expect ';'
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print() {
        if (isInteger) {
            System.out.println("integer " + id + ";");
        } else {
            System.out.println("object " + id + ";");
        }
    }
}

    
