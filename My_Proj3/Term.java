class Term {
	Factor factor;
	Term term;
	int option;
	
	void parse() {
		factor  = new Factor();
		factor.parse();
		if (Parser.scanner.currentToken() == Core.MULTIPLY) {
			option = 1;
		} else if (Parser.scanner.currentToken() == Core.DIVIDE) {
			option = 2;
		}
		if (option != 0) {
			Parser.scanner.nextToken();
			term = new Term();
			term.parse();
		}						
	}
	
	void print() {
		factor.print();
		if (option == 1) {
			System.out.print("*");
			term.print();
		} else if (option == 2) {
			System.out.print("/");
			term.print();
		}
	}

	public int execute(Memory memory) {
		// Always begins with a factor
		int value = factor.execute(memory);
		// either * or / followed by another term (optional)
		if (option == 1) {
			value *= term.execute(memory);
		} else if (option == 2) {
			if(term.execute(memory) == 0){
				System.out.println("ERROR: Division by zero.");
				System.exit(1);
			}
			value /= term.execute(memory);
		}
		return value;
	}
	
}