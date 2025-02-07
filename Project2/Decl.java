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
        Parser.scanner.nextToken();


        // Expect a semicolon ';'
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();

    }

    void print() {
        System.out.println((isInteger ? "integer " : "object ") + id + ";");
    }
}
