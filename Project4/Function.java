import java.util.*;

class Function {
    String name;
    Parameters parameters;
    StmtSeq body;
    
    public Function() {
        parameters = new Parameters();
    }
    
    void parse() {
        Parser.expectedToken(Core.PROCEDURE);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        name = Parser.scanner.getId();
        Parser.scanner.nextToken();
        
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        
        Parser.expectedToken(Core.OBJECT);
        Parser.scanner.nextToken();
        
        parameters.parse();
        
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        
        Parser.expectedToken(Core.IS);
        Parser.scanner.nextToken();
        
        // Check that we have a statement sequence before END
        if (Parser.scanner.currentToken() == Core.END) {
            System.out.println("ERROR: Procedure body missing (no stmt-seq)");
            System.exit(0);
        }
        
        body = new StmtSeq();
        body.parse();
        
        Parser.expectedToken(Core.END);
        Parser.scanner.nextToken();
    }
    
    void execute(ArrayList<String> args) {
        // Create new scope for procedure
        Memory.pushScope();
        
        // Bind parameters to arguments
        ArrayList<String> params = parameters.getParameters();
        if (args.size() != params.size()) {
            System.out.println("ERROR: Argument count mismatch for procedure " + name);
            System.exit(0);
        }
        
        for (int i = 0; i < params.size(); i++) {
            Memory.declareObject(params.get(i));
            Memory.alias(params.get(i), args.get(i));
        }
        
        // Execute procedure body
        body.execute();
        
        // Clean up scope
        Memory.popScope();
    }
    
    void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.print("procedure " + name + "(object ");
        parameters.print();
        System.out.println(") is");
        body.print(indent + 1);
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.println("end");
    }
}
