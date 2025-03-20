import java.util.*;

class Parameters {
    ArrayList<String> paramList;
    
    public Parameters() {
        paramList = new ArrayList<>();
    }
    
    void parse() {
        Parser.expectedToken(Core.ID);
        String param = Parser.scanner.getId();
        paramList.add(param);
        Parser.scanner.nextToken();
        
        while (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            param = Parser.scanner.getId();
            // Check for duplicate parameters
            if (paramList.contains(param)) {
                System.out.println("ERROR: Duplicate parameter name: " + param);
                System.exit(0);
            }
            paramList.add(param);
            Parser.scanner.nextToken();
        }
    }
    
    ArrayList<String> getParameters() {
        return paramList;
    }
    
    void print() {
        for (int i = 0; i < paramList.size(); i++) {
            System.out.print(paramList.get(i));
            if (i < paramList.size() - 1) {
                System.out.print(", ");
            }
        }
    }
}
