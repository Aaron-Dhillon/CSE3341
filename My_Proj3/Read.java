class Read implements Stmt {
	Id argument;
	
	public void parse() {
		Parser.expectedToken(Core.READ);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.LPAREN);
		Parser.scanner.nextToken();
		argument = new Id();
		argument.parse();
		Parser.expectedToken(Core.RPAREN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("read(");
		argument.print();
		System.out.println(");");
	}

	public void execute(Memory memory) {
		// Read an integer value from input (.data files)
		int value = memory.readInput();

		// If doesn't exist, declare the variable
		if(!memory.variableExists(argument.identifier)){
			memory.declareVariable(argument.identifier, true);
		}

		// Set the integer value
		memory.setIntVariable(argument.identifier, value);
	}
}