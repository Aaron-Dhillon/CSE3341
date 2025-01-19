import java.io.*;
class Scanner {
    BufferedReader bReader;
    FileReader fReader;
    // Initialize the scanner
    Scanner(String filename) {
        try{
            fReader = new FileReader(filename);
            bReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }

    }

    // Advance to the next token
    public void nextToken() {

    }

    // Return the current token
    public Core currentToken() {
        return null;
    }

	// Return the identifier string
    public String getId() {
        return null;
    }

	// Return the constant value
    public int getConst() {
        return null;
    }
	
	// Return the character string
    public String getString() {
        return null;
    }

}
