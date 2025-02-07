class Print {
    Expr expr;

    void parse() {
        Parser.expectedToken(Core.PRINT);
        Parser.scanner.nextToken();

        expr = new Expr();
        expr.parse();
    }

    void print() {
        System.out.print("print ");
        expr.print();
        System.out.println(";");
    }
}
