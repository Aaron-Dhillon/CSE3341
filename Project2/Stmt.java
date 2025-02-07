public class Stmt {
    Assign assign;
    Print printStmt;

    void parse() {
        if (Parser.scanner.currentToken() == Core.ID) {
            assign = new Assign();
            assign.parse();
        } else if (Parser.scanner.currentToken() == Core.PRINT) {
            printStmt = new Print();
            printStmt.parse();
        } else {
            System.out.println("ERROR: Unexpected statement.");
            System.exit(1);
        }
    }

    void print() {
        if (assign != null) assign.print();
        if (printStmt != null) printStmt.print();
    }
}
