class Expr {
    Term term;
    Expr expr;
    String operator;

    void parse() {
        term = new Term();
        term.parse();
    
        if (Parser.scanner.currentToken() == Core.ADD || Parser.scanner.currentToken() == Core.SUBTRACT) {
            operator = (Parser.scanner.currentToken() == Core.ADD) ? "+" : "-";
            Parser.scanner.nextToken();
    
            // Check if an operator appears without another term
            if (Parser.scanner.currentToken() == Core.SEMICOLON || Parser.scanner.currentToken() == Core.END) {
                System.out.println("ERROR: Extra operator '" + operator + "' found with no following term.");
                System.exit(1);
            }
    
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
