public class Assign {
    String id, objectId, stringIndex;
    Expr expr;
    boolean isArrayAssign = false, isNewObject = false, isObjectRef = false;

    void parse() {
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        if (Parser.scanner.currentToken() == Core.LSQUARE) {
            isArrayAssign = true;
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.STRING);
            stringIndex = Parser.scanner.getString();
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.RSQUARE);
            Parser.scanner.nextToken();
        }

        if (Parser.scanner.currentToken() == Core.ASSIGN) {
            Parser.scanner.nextToken();
            if (Parser.scanner.currentToken() == Core.NEW) {
                isNewObject = true;
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.OBJECT);
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.LPAREN);
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.STRING);
                objectId = Parser.scanner.getString();
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.COMMA);
                Parser.scanner.nextToken();
                expr = new Expr();
                expr.parse();
                Parser.expectedToken(Core.RPAREN);
                Parser.scanner.nextToken();
            } else {
                expr = new Expr();
                expr.parse();
            }
        } else if (Parser.scanner.currentToken() == Core.COLON) {
            isObjectRef = true;
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            objectId = Parser.scanner.getId();
            Parser.scanner.nextToken();
        }

        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print() {
        if (isArrayAssign) {
            System.out.print(id + "['" + stringIndex + "\''] = ");
        } else {
            System.out.print(id + " ");
        }

        if (isNewObject) {
            System.out.print("= new object('" + objectId + "', ");
            expr.print();
            System.out.print(")");
        } else if (isObjectRef) {
            System.out.print(": " + objectId);
        } else {
            System.out.print("= ");
            expr.print();
        }
        System.out.println(";");
    }
}
