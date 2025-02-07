class Expr {
    Term term;
    Expr expr;
    String operator;

    void parse() {
        term = new Term();
        term.parse();

        if (Parser.scanner.currentToken() == Core.ADD || Parser.scanner.currentToken() == Core.SUBTRACT) {
            // Store actual operator symbol instead of enum name
            if (Parser.scanner.currentToken() == Core.ADD) {
                operator = "+"; 
            } else if (Parser.scanner.currentToken() == Core.SUBTRACT) {
                operator = "-";
            }

            Parser.scanner.nextToken(); // Move past the operator
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
