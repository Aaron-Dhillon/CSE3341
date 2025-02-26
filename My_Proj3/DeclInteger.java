class DeclInteger {
	Id id;
	
	public void parse() {
		Parser.expectedToken(Core.INTEGER);
		Parser.scanner.nextToken();
		id = new Id();
		id.parse();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("integer ");
		id.print();
		System.out.println(";");
	}
}