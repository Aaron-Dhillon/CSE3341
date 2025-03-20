import java.util.*;

class Call implements Stmt {
    String name;
    ArrayList<String> arguments;
    
    public void parse() {
        Parser.expectedToken(Core.BEGIN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.ID);
        name = Parser.scanner.getId();
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.LPAREN);
        Parser.scanner.nextToken();
        
        arguments = new ArrayList<>();
        Parser.expectedToken(Core.ID);
        arguments.add(Parser.scanner.getId());
        Parser.scanner.nextToken();
        
        while (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            arguments.add(Parser.scanner.getId());
            Parser.scanner.nextToken();
        }
        
        Parser.expectedToken(Core.RPAREN);
        Parser.scanner.nextToken();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }
    
    public void execute() {
        Function func = Memory.getFunction(name);
        if (func == null) {
            System.out.println("ERROR: Undefined procedure: " + name);
            System.exit(0);
        }
        
        if (arguments.size() != func.parameters.getParameters().size()) {
            System.out.println("ERROR: Argument count mismatch for procedure " + name);
            System.exit(0);
        }
        
        func.execute(arguments);
    }
    
    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.print("begin " + name + "(");
        for (int i = 0; i < arguments.size(); i++) {
            System.out.print(arguments.get(i));
            if (i < arguments.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(");");
    }
}
