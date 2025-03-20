class DeclSeq {
	Decl decl;
	Function func;
	DeclSeq ds;
	
	void parse() {
		if (Parser.scanner.currentToken() == Core.PROCEDURE) {
			func = new Function();
			func.parse();
		} else {
			decl = new Decl();
			decl.parse();
		}
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void print(int indent) {
		if (func != null) {
			// TODO: Implement function printing if needed
		} else {
			decl.print(indent);
		}
		if (ds != null) {
			ds.print(indent);
		}
	}
	
	void execute() {
		if (func != null) {
			// Register the function in Memory for later use
			Memory.registerFunction(func);
		} else {
			decl.execute();
		}
		if (ds != null) {
			ds.execute();
		}
	}
}