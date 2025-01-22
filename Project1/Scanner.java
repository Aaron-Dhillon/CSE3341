import java.io.*;
import java.util.*;
class Scanner {
    BufferedReader bReader;
    FileReader fReader;
    private int nextChar; // translate to char later
    boolean eof = false;
    private Map<String, Core> keywords = new HashMap<String, Core>();
    private Map<Character, Core> symbols = new HashMap<Character, Core>();

    // Initialize the scanner
    Scanner(String filename) {
        try{
            fReader = new FileReader(filename);
            bReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Initialize the keywords and symbols
        keywords.put("and", Core.AND);
        keywords.put("begin", Core.BEGIN);
        keywords.put("case", Core.CASE);
        keywords.put("do", Core.DO);
        keywords.put("else", Core.ELSE);
        keywords.put("end", Core.END);
        keywords.put("for", Core.FOR);
        keywords.put("if", Core.IF);
        keywords.put("in", Core.IN);
        keywords.put("integer", Core.INTEGER);
        keywords.put("is", Core.IS);
        keywords.put("new", Core.NEW);
        keywords.put("not", Core.NOT);
        keywords.put("object", Core.OBJECT);
        keywords.put("or", Core.OR);
        keywords.put("print", Core.PRINT);
        keywords.put("procedure", Core.PROCEDURE);
        keywords.put("read", Core.READ);
        keywords.put("return", Core.RETURN);
        keywords.put("then", Core.THEN);

        symbols.put('+', Core.ADD);
        symbols.put('-', Core.SUBTRACT);
        symbols.put('*', Core.MULTIPLY);
        symbols.put('/', Core.DIVIDE);
        symbols.put('=', Core.ASSIGN);
        symbols.put('<', Core.LESS);
        symbols.put(':', Core.COLON);
        symbols.put(';', Core.SEMICOLON);
        symbols.put('.', Core.PERIOD);
        symbols.put(',', Core.COMMA);
        symbols.put('(', Core.LPAREN);
        symbols.put(')', Core.RPAREN);
        symbols.put('[', Core.LSQUARE);
        symbols.put(']', Core.RSQUARE);
        symbols.put('{', Core.LCURL);
        symbols.put('}', Core.RCURL);

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
        return 0;
    }
	
	// Return the character string
    public String getString() {
        return null;
    }

    private void moveChar() {
        try {
            nextChar = bReader.read();
            if (nextChar == -1) {
                eof = true;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
            System.exit(1);
        }
    }

    private char consumeChar(){
        char c = (char) nextChar;
        moveChar();
        return c;
    }


}
