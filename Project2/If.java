class If {
    Cond condition;
    StmtSeq thenStmtSeq;
    StmtSeq elseStmtSeq;
    boolean hasElse = false;
    
    void parse() {
    
            // Expect 'IF'
            Parser.expectedToken(Core.IF);
            Parser.scanner.nextToken();
    
            // Parse the condition
            condition = new Cond();
            condition.parse();
    
            // Expect 'THEN'
            if (Parser.scanner.currentToken() != Core.THEN) {
                System.out.println("ERROR: Missing 'then' after if condition.");
                System.exit(1);
            }
            Parser.scanner.nextToken();
    
            // Parse the 'THEN' block
            Parser.enterScope();
            thenStmtSeq = new StmtSeq();
            thenStmtSeq.parse();
            Parser.exitScope();
    
            // **If there's no ELSE, END should appear here**
            if (Parser.scanner.currentToken() == Core.END) {
                Parser.scanner.nextToken(); // Consume 'END'
                return; // Exit parsing
            }
    
            // **Handle ELSE properly**
            if (Parser.scanner.currentToken() == Core.ELSE) {
                hasElse = true;
                Parser.scanner.nextToken();
    
                // **Check if a valid statement follows ELSE**
                if (!isValidStatementStart(Parser.scanner.currentToken())) {
                    System.out.println("ERROR: ELSE must be followed by a statement. (IF ends with ELSE)");
                    System.exit(1);
                }
    
                Parser.enterScope();
                elseStmtSeq = new StmtSeq();
                elseStmtSeq.parse();
                Parser.exitScope();
    
                // **Ensure ELSE is followed by END**
                if (Parser.scanner.currentToken() != Core.END) {
                    System.out.println("ERROR: Missing 'end' for if statement.");
                    System.exit(1);
                }
                Parser.scanner.nextToken(); // Consume 'END'
            } else {
                System.out.println("ERROR: Missing 'end' for if statement.");
                System.exit(1);
            }
        }
    
        // **New helper function to check if a token starts a valid statement**
        private boolean isValidStatementStart(Core token) {
            return token == Core.ID ||
                   token == Core.PRINT ||
                   token == Core.IF ||
                   token == Core.FOR ||
                   token == Core.READ ||
                   token == Core.INTEGER ||
                   token == Core.OBJECT;
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
