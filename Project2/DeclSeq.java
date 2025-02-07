public class DeclSeq {
    Decl d;
    DeclSeq ds;

    void parse() {
        d = new Decl();
        d.parse();
        if (Parser.scanner.currentToken() != Core.SEMICOLON) {
            ds = new DeclSeq();
            ds.parse();
        }
    }

    void print() {
        d.print();
        if (ds != null) {
            ds.print();
        }
    }
}
