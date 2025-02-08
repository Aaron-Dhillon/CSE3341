class Term {
    Factor factor;
    Term term;
    String operator;

    void parse() {
        factor = new Factor();
        factor.parse();

        if (Parser.scanner.currentToken() == Core.MULTIPLY || Parser.scanner.currentToken() == Core.DIVIDE) {
            if(Parser.scanner.currentToken() == Core.MULTIPLY) {
                operator = "*";
            } else {
                operator = "/";
            }
            Parser.scanner.nextToken();
            term = new Term();
            term.parse();
        }
    }

    void print() {
        factor.print();
        if (term != null) {
            System.out.print(" " + operator + " ");
            term.print();
        }
    }

    String getFirstVariable() {
        return factor.getFirstVariable();
    }
}
