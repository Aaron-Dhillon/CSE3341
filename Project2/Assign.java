public class Assign {
    String id;
    Expr expr;

    void parse() {
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        Parser.expectedToken(Core.ASSIGN);
        Parser.scanner.nextToken();

        expr = new Expr();
        expr.parse();
    }

    void print() {
        System.out.print(id + " = ");
        expr.print();
        System.out.println(";");
    }
}
