public class Decl {
    String id;
    
    void parse() {
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.expectedToken(Core.COLON);
        Parser.scanner.nextToken();
    
        Parser.expectedToken(Core.INTEGER);
        Parser.scanner.nextToken();
    }
    
    void print() {
        System.out.println(id + " : integer;");
    }
}
    
