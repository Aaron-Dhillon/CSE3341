
class Procedure {
    String name;
    DeclSeq ds;
    StmtSeq ss;

    void parse() {
        // Expect the PROCEDURE token
        Parser.expectedToken(Core.PROCEDURE);
        Parser.scanner.nextToken();

        // Parse the procedure name
        Parser.expectedToken(Core.ID);
        name = Parser.scanner.getId();
        Parser.scanner.nextToken();

        // Expect the IS token
        Parser.expectedToken(Core.IS);
        Parser.scanner.nextToken();

        // Parse declarations if they exist
        if (Parser.scanner.currentToken() != Core.BEGIN) {
            ds = new DeclSeq();
            ds.parse();
        }

        // Parse the BEGIN token
        Parser.expectedToken(Core.BEGIN);
        Parser.scanner.nextToken();

        // Parse the statement sequence
        ss = new StmtSeq();
        ss.parse();

        // Parse the END token
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();

        // Expect the End of Stream (EOS)
        Parser.expectedToken(Core.EOS);
    }

    void print() {
        // Print the procedure header
        System.out.println("procedure " + name + " is");

        // Print the declarations, if present
        if (ds != null) {
            ds.print();
        }

        // Print the BEGIN block
        System.out.println("begin ");

        // Print the statement sequence
        ss.print();

        // Print the END block
        System.out.println("end");
    }
}
