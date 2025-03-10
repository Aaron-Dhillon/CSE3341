class Decl implements Stmt {
	DeclInteger declInt;
	DeclObj declObj;
	
	public void parse() {
		if (Parser.scanner.currentToken() == Core.INTEGER) {
			declInt = new DeclInteger();
			declInt.parse();
		} else {
			declObj = new DeclObj();
			declObj.parse();
		}
	}
	
	public void print(int indent) {
		if (declInt != null) {
			declInt.print(indent);
		} else {
			declObj.print(indent);
		}
	}

	public void execute(Memory memory) {
		// Either a int or object declaration
		if (declInt != null) {
			declInt.execute(memory);
		} else {
			declObj.execute(memory);
		}
	}	
}