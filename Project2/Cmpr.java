public class Cmpr {
    Expr left;
    Expr right;
    String comparisonOperator;

    void parse() {
        left = new Expr();
        left.parse();

        if (Parser.scanner.currentToken() == Core.EQUAL || Parser.scanner.currentToken() == Core.LESS) {
            if (Parser.scanner.currentToken() == Core.EQUAL) {
                comparisonOperator = "==";
            } else {
                comparisonOperator = "<";
            }
            Parser.scanner.nextToken();
        } else {
            System.out.println("ERROR: Invalid comparison operator in condition.");
            System.exit(1);
        }

        right = new Expr();
        right.parse();
    }

    void print() {
        left.print();
        System.out.print(" " + comparisonOperator + " ");
        right.print();
    }
}
