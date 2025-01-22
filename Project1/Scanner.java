import java.io.*;
import java.util.*;
class Scanner {
    BufferedReader bReader;
    FileReader fReader;
    StringBuilder sBuilder;
    private String currentIDString;
    private String currentString;
    private int currentConstValue;
    private int nextChar; // translate to char later
    boolean eof = false;
    private Core currentToken;
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
        consumeWhitespace();
        if(eof){
            currentToken = Core.EOS;
            return;
        }

        char currentChar = checkNextChar();
        if(Character.isLetter(currentChar)){
           String keywordOrId = buildKeywordOrId();
           if(keywords.containsKey(keywordOrId)){
               currentToken = keywords.get(keywordOrId);
                return;
           } else {
               currentToken = Core.ID;
               currentIDString = keywordOrId;
               return;
           }
        }else if(Character.isDigit(currentChar)){
            int constValue = buildConst();
            if(constValue <0 || constValue > 1000003){
                currentToken = Core.ERROR;
                return;
            }else{
                currentToken = Core.CONST;
                currentConstValue = constValue;
                return;
            }
        }else if ('\'' == currentChar){
            consumeChar();
            sBuilder = new StringBuilder();
            while('\'' != checkNextChar()){
                sBuilder.append(consumeChar());
            }
            consumeChar();
            currentToken = Core.STRING;
            return;
        }else{
            System.out.println("IDK");
        }
    }

    // Return the current token
    public Core currentToken() {
        return currentToken;
    }

	// Return the identifier string
    public String getId() {
        if(currentToken == Core.ID){
            return sBuilder.toString();
        }else{
            System.out.println("Error: Not an ID");
            return null;
        }
    }

	// Return the constant value
    public int getConst() {
        if(currentToken == Core.CONST){
            return currentConstValue;
        }else{
            System.out.println("Error: Not a CONST");
            return -1;
        }    
    }
	
	// Return the character string
    public String getString() {
        if(currentToken == Core.STRING){
            return currentString;
        }else{
            System.out.println("Error: Not a STRING");
            return null;
        }
        
    }

    private void moveToNextChar() {
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

    private char checkNextChar(){
        if(eof){
            return '\0';
        }
        return (char) nextChar;
    }

    private char consumeChar(){
        char prev = (char) nextChar;
        moveToNextChar();
        return prev;
    }

    private void consumeWhitespace() {
        while ( !eof && Character.isWhitespace(checkNextChar())) {
            moveToNextChar();
        }
    }

    private String buildKeywordOrId(){
        sBuilder = new StringBuilder();
        while(Character.isLetterOrDigit(checkNextChar())){
            sBuilder.append(consumeChar());
        }
        return sBuilder.toString();
    }

    private int buildConst(){
        sBuilder = new StringBuilder();
        while(Character.isDigit(checkNextChar())){
            sBuilder.append(consumeChar());
        }
        return Integer.parseInt(sBuilder.toString());
    }


}
