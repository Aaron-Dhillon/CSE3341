class Cond {
	Cmpr cmpr;
	Cond cond;
	int option;
	
	void parse() {
		option = 0;
		if (Parser.scanner.currentToken() == Core.NOT){
			option = 1;
			Parser.scanner.nextToken();
			cond = new Cond();
			cond.parse();
		} else if (Parser.scanner.currentToken() == Core.LSQUARE) {
			option = 2;
			Parser.scanner.nextToken();
			cond = new Cond();
			cond.parse();
			Parser.expectedToken(Core.RSQUARE);
			Parser.scanner.nextToken();
		} else {
			cmpr = new Cmpr();
			cmpr.parse();
			if (Parser.scanner.currentToken() == Core.OR) {
				option = 3;
				Parser.scanner.nextToken();
				cond = new Cond();
				cond.parse();
			} else if (Parser.scanner.currentToken() == Core.AND) {
				option = 4;
				Parser.scanner.nextToken();
				cond = new Cond();
				cond.parse();
			}
		}
	}
	
	void print() {
		if (option == 1) {
			System.out.print("not ");
			cond.print();
		} else if (option == 2) {
			System.out.print("[");
			cond.print();
			System.out.print("]");
		}else {
			cmpr.print();
			if (cond != null) {
				if (option == 3) System.out.print(" or ");
				if (option == 4) System.out.print(" and ");
				cond.print();
			}
		}
	}

	public boolean execute(Memory memory) {
		if (option == 0) {
			return cmpr.execute(memory);
		} else if (option == 1) {
			return !cond.execute(memory);
		} else if(option == 2) {
			return cond.execute(memory);
		} else if (option == 3) {
			return cmpr.execute(memory) || cond.execute(memory);
		} else if (option == 4) {
			return cmpr.execute(memory) && cond.execute(memory);
		}else{
			System.out.println("ERROR: Invalid option in Cond");
			return false; // Should never reach this point
		}
		
	}


}