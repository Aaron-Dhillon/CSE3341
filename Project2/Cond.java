class Cond {
    Cond leftCond, rightCond;
    Cmpr comparison;
    String operator;
    boolean isNegated = false, isGrouped = false;

    void parse() {
        if (Parser.scanner.currentToken() == Core.NOT) {
            isNegated = true;
            Parser.scanner.nextToken();
            leftCond = new Cond();
            leftCond.parse();
        } else if (Parser.scanner.currentToken() == Core.LSQUARE) {
            isGrouped = true;
            Parser.scanner.nextToken();
            leftCond = new Cond();
            leftCond.parse();
            Parser.expectedToken(Core.RSQUARE);
            Parser.scanner.nextToken();
        } else {
            comparison = new Cmpr();
            comparison.parse();
        }

        if (Parser.scanner.currentToken() == Core.OR || Parser.scanner.currentToken() == Core.AND) {
            operator = Parser.scanner.currentToken().toString();
            Parser.scanner.nextToken();
            rightCond = new Cond();
            rightCond.parse();
        }
    }

    void print() {
        if (isNegated) {
            System.out.print("not ");
            leftCond.print();
        } else if (isGrouped) {
            System.out.print("[");
            leftCond.print();
            System.out.print("]");
        } else {
            comparison.print();
        }
        if (operator != null) {
            System.out.print(" " + operator + " ");
            rightCond.print();
        }
    }
}
