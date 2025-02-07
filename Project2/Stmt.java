public class Stmt {
    Assign assign;
    Print printStmt;
    If ifStmt;
    Loop loopStmt;
    Read readStmt;
    Decl declStmt;

    void parse() {
        switch (Parser.scanner.currentToken()) {
            case ID:
                // Assignment statement
                assign = new Assign();
                assign.parse();
                break;
            case PRINT:
                // Print statement
                printStmt = new Print();
                printStmt.parse();
                break;
            case IF:
                // If statement
                ifStmt = new If();
                ifStmt.parse();
                break;
            case FOR:
                // Loop statement
                loopStmt = new Loop();
                loopStmt.parse();
                break;
            case READ:
                // Read statement
                readStmt = new Read();
                readStmt.parse();
                break;
            case INTEGER:
            case OBJECT:
                // Declaration statement
                declStmt = new Decl();
                declStmt.parse();
                break;
            default:
                // Error: unexpected token
                System.out.println("ERROR: Unexpected statement.");
                System.exit(1);
        }
    }

    void print() {
        if (assign != null) assign.print();
        if (printStmt != null) printStmt.print();
        if (ifStmt != null) ifStmt.print();
        if (loopStmt != null) loopStmt.print();
        if (readStmt != null) readStmt.print();
        if (declStmt != null) declStmt.print();
    }
}
