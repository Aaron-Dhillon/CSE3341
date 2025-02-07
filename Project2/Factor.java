class Factor {
    String id;
    int constant;
    Expr expr;
    boolean isConst = false;
    boolean isExpr = false;

    void parse() {
        if (Parser.scanner.currentToken() == Core.ID) {
            id = Parser.scanner.getId();
            Parser.scanner.nextToken();
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
            System.out.println("ERROR: Invalid Factor");
            System.exit(1);
        }
    }

    void print() {
        if (id != null) {
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
