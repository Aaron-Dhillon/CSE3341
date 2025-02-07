public class StmtSeq {
    Stmt s;
    StmtSeq ss;

    void parse() {
        s = new Stmt();
        s.parse();

        // Check if the next token starts a valid statement
        switch (Parser.scanner.currentToken()) {
            case ID:       // Assignment statement
            case PRINT:    // Print statement
            case IF:       // If statement
            case FOR:      // Loop statement
            case READ:     // Read statement
            case INTEGER:  // Declaration statement
            case OBJECT:   // Declaration statement
                ss = new StmtSeq();
                ss.parse();
                break;
            default:
                // Stop parsing StmtSeq if the next token does not start a new statement
                break;
        }
    }

    void print() {
        s.print();
        if (ss != null) {
            ss.print();
        }
    }
}
