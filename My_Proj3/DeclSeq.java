class DeclSeq {
	Decl decl;
	DeclSeq ds;
	
	void parse() {
		decl = new Decl();
		decl.parse();
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void print(int indent) {
		decl.print(indent);
		if (ds != null) {
			ds.print(indent);
		}
	}

	public void execute(Memory memory) {

		// has at least one declaration
		decl.execute(memory);

		// more declarations (optional)
		if (ds != null) {
			ds.execute(memory);
		}
	}

}