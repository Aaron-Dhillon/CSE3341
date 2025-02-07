public class Decl {
    String id;
    boolean isInteger; 

    void parse() {
        if (Parser.scanner.currentToken() == Core.INTEGER) {
            isInteger = true;
        } else if (Parser.scanner.currentToken() == Core.OBJECT) {
            isInteger = false;
        } else {
            System.out.println("ERROR: Expected 'integer' or 'object' in declaration.");
            System.exit(1);
        }
        Parser.scanner.nextToken();

        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print() {
        System.out.println((isInteger ? "integer " : "object ") + id + ";");
    }
}
