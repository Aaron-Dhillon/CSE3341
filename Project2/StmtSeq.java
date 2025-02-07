public class StmtSeq {
    Stmt s;
    StmtSeq ss;

    void parse() {
        s = new Stmt();
        s.parse();

        if (Parser.scanner.currentToken() == Core.SEMICOLON) {
            Parser.scanner.nextToken();  // Consume ;
            if (Parser.scanner.currentToken() != Core.END) {  
                ss = new StmtSeq();
                ss.parse();
            }
        }
    }

    void print() {
        s.print();
        if (ss != null) {
            ss.print();
        }
    }
}


