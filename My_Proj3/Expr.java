class Expr {
	Term term;
	Expr expr;
	int option;
	
	void parse() {
		term  = new Term();
		term.parse();
		if (Parser.scanner.currentToken() == Core.ADD) {
			option = 1;
		} else if (Parser.scanner.currentToken() == Core.SUBTRACT) {
			option = 2;
		}
		if (option != 0) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
		}						
	}
	
	void print() {
		term.print();
		if (option == 1) {
			System.out.print("+");
			expr.print();
		} else if (option == 2) {
			System.out.print("-");
			expr.print();
		}
	}

	public int execute(Memory memory) {

		// Always begins with a term
		int value = term.execute(memory);

		// either + or - followed by another expression (optional)
		if (option == 1) {
			expr.execute(memory);
			value += expr.execute(memory);
		} else if (option == 2) {
			expr.execute(memory);
			value -= expr.execute(memory);
		}
		return value;
	}
}