import java.util.*;

class Function {
    String name;
    ArrayList<String> parameters;
    StmtSeq body;
    
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
        
        parameters = new ArrayList<>();
        Parser.expectedToken(Core.ID);
        parameters.add(Parser.scanner.getId());
        Parser.scanner.nextToken();
        
        while (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            String param = Parser.scanner.getId();
            // Check for duplicate parameters
            if (parameters.contains(param)) {
                System.out.println("ERROR: Duplicate parameter name: " + param);
                System.exit(0);
            }
            parameters.add(param);
            Parser.scanner.nextToken();
        }
        
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
        for (int i = 0; i < parameters.size(); i++) {
            Memory.declareObject(parameters.get(i));
            Memory.alias(parameters.get(i), args.get(i));
        }
        
        // Execute procedure body
        body.execute();
        
        // Clean up scope
        Memory.popScope();
    }
}
