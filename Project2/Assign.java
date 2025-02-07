public class Assign {
    String id, objectId, stringIndex;
    Expr expr;
    boolean isArrayAssign = false, isNewObject = false, isObjectRef = false;

    void parse() {
        // Expect an identifier
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();
        Parser.scanner.nextToken();

        // Handle array assignment id['string']
        if (Parser.scanner.currentToken() == Core.LSQUARE) {
            isArrayAssign = true;
            Parser.scanner.nextToken();

            if (Parser.scanner.currentToken() != Core.STRING) {
                System.out.println("ERROR: Expected a string index inside [].");
                System.exit(1);
            }

            stringIndex = Parser.scanner.getString();
            Parser.scanner.nextToken();

            Parser.expectedToken(Core.RSQUARE);
            Parser.scanner.nextToken();
        }

        // Ensure correct assignment operator '=' (not '==')
        if (Parser.scanner.currentToken() == Core.EQUAL) {
            System.out.println("ERROR: Found '==' instead of '=' in assignment.");
            System.exit(1);
        }

        if (Parser.scanner.currentToken() == Core.ASSIGN) {
            Parser.scanner.nextToken();

            // Handle new object assignment
            if (Parser.scanner.currentToken() == Core.NEW) {
                isNewObject = true;
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.OBJECT);
                Parser.scanner.nextToken();
                Parser.expectedToken(Core.LPAREN);
                Parser.scanner.nextToken();

                if (Parser.scanner.currentToken() != Core.STRING) {
                    System.out.println("ERROR: Expected string object name in new object().");
                    System.exit(1);
                }

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
        } else {
            System.out.println("ERROR: Expected '=' or ':' after identifier '" + id + "'.");
            System.exit(1);
        }

        // Ensure semicolon at the end of the assignment
        if (Parser.scanner.currentToken() != Core.SEMICOLON) {
            System.out.println("ERROR: Missing semicolon ';' at the end of assignment.");
            System.exit(1);
        }
        Parser.scanner.nextToken();
    }

    void print() {
        if (isArrayAssign) {
            System.out.print(id + "['" + stringIndex + "'] = ");
        } else {
            System.out.print(id + " = ");
        }

        if (isNewObject) {
            System.out.print("new object('" + objectId + "', ");
            expr.print();
            System.out.print(")");
        } else if (isObjectRef) {
            System.out.print(objectId);
        } else {
            expr.print();
        }
        System.out.println(";");
    }
}
