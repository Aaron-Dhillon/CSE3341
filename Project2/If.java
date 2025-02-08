class If {
    Cond condition;
    StmtSeq thenStmtSeq;
    StmtSeq elseStmtSeq;
    boolean hasElse = false;

    void parse() {
        // Expect 'if'
        Parser.expectedToken(Core.IF);
        Parser.scanner.nextToken();

        // Parse the condition
        condition = new Cond();
        condition.parse();

        // Expect 'then'
        if (Parser.scanner.currentToken() != Core.THEN) {
            System.out.println("ERROR: Missing 'then' after if condition.");
            System.exit(1);
        }
        Parser.scanner.nextToken();

        // Enter a new scope for the THEN block
        Parser.enterScope();
        thenStmtSeq = new StmtSeq();
        thenStmtSeq.parse();
        Parser.exitScope(); // Exit scope after parsing THEN block

        // Check if 'else' exists
        if (Parser.scanner.currentToken() == Core.ELSE) {
            hasElse = true;
            Parser.scanner.nextToken();

            // Enter a new scope for the ELSE block
            Parser.enterScope();
            elseStmtSeq = new StmtSeq();
            elseStmtSeq.parse();
            Parser.exitScope(); // Exit scope after parsing ELSE block
        }

        // Expect 'end'
        if (Parser.scanner.currentToken() != Core.END) {
            System.out.println("ERROR: Missing 'end' for if statement.");
            System.exit(1);
        }
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
