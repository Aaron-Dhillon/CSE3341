public class Loop {
    String id;
    Expr initExpr;
    Cond condition;
    Expr updateExpr;
    StmtSeq stmtSeq;

    void parse() {
        // Expect 'for' token
        Parser.expectedToken(Core.FOR);
        Parser.scanner.nextToken();

        // Expect '(' token
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();

        // Expect loop variable (ID)
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        // Expect '=' token
        Parser.expectedToken(Core.ASSIGN);
        Parser.scanner.nextToken();

        // Parse initialization expression
        initExpr = new Expr();
        initExpr.parse();

        // Expect ';' token
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();

        // Parse condition
        condition = new Cond();
        condition.parse();

        // Expect ';' token
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();

        // Parse update expression
        updateExpr = new Expr();
        updateExpr.parse();

        // Expect ')' token
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();

        // Expect 'do' token
        Parser.expectedToken(Core.DO);
        Parser.scanner.nextToken();

        // Parse statement sequence
        stmtSeq = new StmtSeq();
        stmtSeq.parse();

        // Expect 'end' token
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
    }

    void print() {
        System.out.print("for (" + id + " = ");
        initExpr.print();
        System.out.print("; ");
        condition.print();
        System.out.print("; ");
        updateExpr.print();
        System.out.println(") do");

        stmtSeq.print();

        System.out.println("end");
    }
}
