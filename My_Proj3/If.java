
class If implements Stmt {
	Cond cond;
	StmtSeq ss1;
	StmtSeq ss2;
	
	public void parse() {
		Parser.scanner.nextToken();
		cond = new Cond();;
		cond.parse();
		Parser.expectedToken(Core.THEN);
		Parser.scanner.nextToken();
		ss1 = new StmtSeq();
		ss1.parse();
		if (Parser.scanner.currentToken() == Core.ELSE) {
			Parser.scanner.nextToken();
			ss2 = new StmtSeq();
			ss2.parse();
		}
		Parser.expectedToken(Core.END);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("	");
		}
		System.out.print("if ");
		cond.print();
		System.out.println(" then");
		ss1.print(indent+1);
		if (ss2 != null) {
			for (int i=0; i<indent; i++) {
				System.out.print("	");
			}
			System.out.println("else");
			ss2.print(indent+1);
		}
		for (int i=0; i<indent; i++) {
			System.out.print("	");
		}
		System.out.println("end");
	}


	public void execute(Memory memory) {

		// Loop begins with checking the condition
		if(cond.execute(memory)) {
			// Has its own scope
			memory.enterScope();
			
			// Execute the statements
			ss1.execute(memory);

			// Exit the scope
			memory.exitScope();

		
		} else if(ss2 != null) { // If there is an else statement
			memory.enterScope();
			ss2.execute(memory);
			memory.exitScope();
		}
	}

}