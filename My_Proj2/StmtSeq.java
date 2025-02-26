public class StmtSeq {
    Stmt s;
    StmtSeq ss;

    void parse() {
        s = new Stmt();
        s.parse();

        // Check if the next token starts a valid statement
        switch (Parser.scanner.currentToken()) {
            case ID:
            case PRINT:
            case IF:
            case FOR:
            case READ:
            case INTEGER:
            case OBJECT:
                ss = new StmtSeq();
                ss.parse();
                break;
            default:
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
