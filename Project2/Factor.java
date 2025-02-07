class Factor {
    String id;
    int constant;
    Expr expr;
    String stringIndex; // Ensures only a string inside id[string]
    boolean isConst = false;
    boolean isExpr = false;
    boolean isArrayAccess = false;

    void parse() {
        if (Parser.scanner.currentToken() == Core.ID) {
            id = Parser.scanner.getId();
            Parser.scanner.nextToken();

            // Check if it's an array access: id[string]
            if (Parser.scanner.currentToken() == Core.LSQUARE) {
                isArrayAccess = true;
                Parser.scanner.nextToken(); // Consume '['

                // Expect only a STRING inside the brackets
                Parser.expectedToken(Core.STRING);
                stringIndex = Parser.scanner.getString();
                Parser.scanner.nextToken(); // Consume the string

                Parser.expectedToken(Core.RSQUARE);
                Parser.scanner.nextToken(); // Consume ']'
            }
        } else if (Parser.scanner.currentToken() == Core.CONST) {
            constant = Parser.scanner.getConst();
            isConst = true;
            Parser.scanner.nextToken();
        } else if (Parser.scanner.currentToken() == Core.LPAREN) {
            isExpr = true;
            Parser.scanner.nextToken();
            expr = new Expr();
            expr.parse();
            Parser.expectedToken(Core.RPAREN);
            Parser.scanner.nextToken();
        } else {
            throw new RuntimeException("ERROR: Invalid Factor - Unexpected token " + Parser.scanner.currentToken());
        }
    }

    void print() {
        if (isArrayAccess) {
            System.out.print(id + "[" + "\"" + stringIndex + "\"" + "]");
        } else if (id != null) {
            System.out.print(id);
        } else if (isConst) {
            System.out.print(constant);
        } else if (isExpr) {
            System.out.print("(");
            expr.print();
            System.out.print(")");
        }
    }
}
