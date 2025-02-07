class Expr {
    Term term;
    Expr expr;
    String operator;

    void parse() {
        term = new Term();
        term.parse();

        if (Parser.scanner.currentToken() == Core.ADD || Parser.scanner.currentToken() == Core.SUBTRACT) {
            operator = Parser.scanner.currentToken().toString();
            Parser.scanner.nextToken();
            expr = new Expr();
            expr.parse();
        }
    }

    void print() {
        term.print();
        if (expr != null) {
            System.out.print(" " + operator + " ");
            expr.print();
        }
    }
}
