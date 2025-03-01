class Factor {
	Id id;
	String key;
	int constant;
	Expr expr;
	
	void parse() {
		if (Parser.scanner.currentToken() == Core.ID) {
			id = new Id();
			id.parse();
			if (Parser.scanner.currentToken() == Core.LSQUARE) {
				Parser.scanner.nextToken();
				key = Parser.scanner.getString();
				Parser.scanner.nextToken();
				Parser.expectedToken(Core.RSQUARE);
				Parser.scanner.nextToken();
			}
		} else if (Parser.scanner.currentToken() == Core.CONST) {
			constant = Parser.scanner.getConst();
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.LPAREN) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else {
			System.out.println("ERROR: Expected ID, CONST, LPAREN, recieved " + Parser.scanner.currentToken());
			System.exit(0);
		}
	}
	
	void print() {
		if (id != null) {
			id.print();
			if (key != null) {
				System.out.print("['" + key + "']");
			}
		} else if (expr != null) {
			System.out.print("(");
			expr.print();
			System.out.print(")");
		} else {
			System.out.print(constant);
		}
	}

	public int execute(Memory memory) {

		// Check if it as an object reference or integer
		if (id != null) {

			// If key is not null, it is an object reference
			if (key != null) {
				return memory.getObjectKey(id.identifier, key);
			} else {
				return memory.getIntVariableValue(id.identifier);
			}
		} else if (expr != null) { // If it is an expression
			return expr.execute(memory);
		} else { // otherwise it is a constant
			return constant;
		}
	}
}