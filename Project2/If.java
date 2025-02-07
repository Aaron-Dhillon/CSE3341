class If {
    Cond condition;
    StmtSeq thenStmtSeq;
    StmtSeq elseStmtSeq;
    boolean hasElse = false;

    void parse() {
        Parser.expectedToken(Core.IF);
        Parser.scanner.nextToken();

        condition = new Cond();
        condition.parse();

        Parser.expectedToken(Core.THEN);
        Parser.scanner.nextToken();

        thenStmtSeq = new StmtSeq();
        thenStmtSeq.parse();

        if (Parser.scanner.currentToken() == Core.ELSE) {
            hasElse = true;
            Parser.scanner.nextToken();
            elseStmtSeq = new StmtSeq();
            elseStmtSeq.parse();
        }

        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
    }

    void print() {
        System.out.print("if ");
        condition.print();
        System.out.println(" then");
        thenStmtSeq.print();
        if (hasElse) {
            System.out.println("else");
            elseStmtSeq.print();
        }
        System.out.println("end");
    }
}
