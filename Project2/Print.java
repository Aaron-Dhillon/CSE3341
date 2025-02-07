class Print {
    Expr expr;

    void parse() {
        Parser.expectedToken(Core.PRINT);
        Parser.scanner.nextToken();

        expr = new Expr();
        expr.parse();

        // Consume the semicolon after the print statement
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();  // MOVE PAST SEMICOLON
    }

    void print() {
        System.out.print("print ");
        expr.print();
        System.out.println(";");
    }
}
