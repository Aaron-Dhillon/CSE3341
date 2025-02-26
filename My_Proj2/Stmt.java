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
                assign = new Assign();
                assign.parse();
                break;
            case PRINT:
                printStmt = new Print();
                printStmt.parse();
                break;
            case IF:
                ifStmt = new If();
                ifStmt.parse();
                break;
            case FOR:
                loopStmt = new Loop();
                loopStmt.parse();
                break;
            case READ:
                readStmt = new Read();
                readStmt.parse();
                break;
            case INTEGER:
            case OBJECT:
                declStmt = new Decl();
                declStmt.parse();
                break;
            default:
                System.out.println("ERROR: Unexpected statement. Found token: " + Parser.scanner.currentToken());
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
