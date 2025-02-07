
class Main {
	public static void main(String[] args) {
		//Initialize the scanner witht the input file
		Scanner S = new Scanner(args[0]);
		Parser.scanner = S;

		Procedure p = new Procedure();

		p.parse();

		p.print();
	}	
}