public class DeclSeq {
    Decl d;
    DeclSeq ds; // For multiple declarations

    void parse() {
        
        d = new Decl();
        d.parse(); // Parse first declaration
        
        // Check if another declaration follows (integer or object)
        if (Parser.scanner.currentToken() == Core.INTEGER || Parser.scanner.currentToken() == Core.OBJECT) {
            ds = new DeclSeq(); // Recursively parse next declaration
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
