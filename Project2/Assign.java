public class Assign {
    String id, objectId, stringIndex;
    Expr expr;
    boolean isArrayAssign = false, isNewObject = false, isObjectRef = false;
    boolean isPropertyAssign = false; // Tracks object property assignments

    void parse() {
        // Expect an identifier
        Parser.expectedToken(Core.ID);
        id = Parser.scanner.getId();

        // Check if variable is declared in any valid scope
        if (!Parser.isVariableDeclared(id)) {
            System.out.println("ERROR: Variable '" + id + "' used before declaration.");
            System.exit(1);
        }

        Parser.scanner.nextToken();

        // Handle array/object property assignment id['string']
        if (Parser.scanner.currentToken() == Core.LSQUARE) {
            Parser.scanner.nextToken();

            if (Parser.scanner.currentToken() != Core.STRING) {
                System.out.println("ERROR: Expected a string index inside [].");
                System.exit(1);
            }

            stringIndex = Parser.scanner.getString();
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.RSQUARE);
            Parser.scanner.nextToken();

            // Determine if this is an object property or array access
            if (Parser.isObject(id)) {
                isPropertyAssign = true;  // Object property assignment (a['x'])
            } else {
                isArrayAssign = true;  // Regular array indexing (integer arrays)
            }
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

                // Ensure assignment target is an object
                if (!Parser.isObject(id) && !isPropertyAssign) {
                    System.out.println("ERROR: Cannot assign an object to an integer variable '" + id + "'.");
                    System.exit(1);
                }
            } else {
                expr = new Expr();
                expr.parse();

                // Ensure all variables in the expression are declared
                String undeclaredVar = expr.getFirstVariable();
                if (undeclaredVar != null && !Parser.isVariableDeclared(undeclaredVar)) {
                    System.out.println("ERROR: Variable '" + undeclaredVar + "' used before declaration.");
                    System.exit(1);
                }

                // Prevent assigning integer expressions to object variables
                if (Parser.isObject(id) && !isPropertyAssign) {
                    System.out.println("ERROR: Cannot assign an integer expression to an object variable '" + id + "'.");
                    System.exit(1);
                }

                // Prevent assigning a property to an integer variable
                if (!Parser.isObject(id) && isPropertyAssign) {
                    System.out.println("ERROR: Cannot assign an object property '" + id + "['" + stringIndex + "']' to an integer variable.");
                    System.exit(1);
                }
            }
        } else if (Parser.scanner.currentToken() == Core.COLON) {
            isObjectRef = true;
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            objectId = Parser.scanner.getId();

            // Ensure the referenced object exists in any valid scope
            if (!Parser.isVariableDeclared(objectId)) {
                System.out.println("ERROR: Object '" + objectId + "' used before declaration.");
                System.exit(1);
            }

            Parser.scanner.nextToken();
        } else {
            System.out.println("ERROR: Expected '=' or ':' after identifier '" + id + "'.");
            System.exit(1);
        }

        // Ensure semicolon at the end of the assignment
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    void print() {
        if (isArrayAssign) {
            System.out.print(id + "['" + stringIndex + "'] = ");
        } else if (isPropertyAssign) {
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
