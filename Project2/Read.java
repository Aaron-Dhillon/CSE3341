public class Read {
    String id;

    void parse() {
        // Expect 'read' token
        Parser.expectedToken(Core.READ);
        Parser.scanner.nextToken();

        // Expect '(' token
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();

        // Expect ID token
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        // Expect ')' token
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();

        // Expect ';' token
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print() {
        System.out.println("read (" + id + ");");
    }
}
