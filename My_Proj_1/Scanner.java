import java.io.*;
import java.util.*;
class Scanner {

    // intitialize all private variables to the scanner
    private BufferedReader bReader;
    private FileReader fReader;
    private StringBuilder sBuilder;
    private String currentIDString;
    private String currentString;
    private int currentConstValue;
    private int nextChar; // translate to char later
    private boolean eof;
    private Core currentToken = null;
    private Map<String, Core> keywords;
    private Map<Character, Core> symbols;

    // Initialize the scanner
    Scanner(String filename) {
        try{
            //attempt to open the file
            fReader = new FileReader(filename);
            bReader = new BufferedReader(fReader);
            sBuilder = new StringBuilder();

            //initialize all the variables
            keywords = new HashMap<String, Core>();
            symbols = new HashMap<Character, Core>();
            initKeywordsAndSymbols();
            moveToNextChar();
            eof = false;
        } catch (FileNotFoundException e) {

            //error handling if file is not found
            System.out.println("File not found");
            System.exit(1);
        }

    }


    // Parse the current token and move to the next token
    public void nextToken() {
        consumeWhitespace();
        if(eof){
            currentToken = Core.EOS;
            return;
        }

        // finde the next character and begin building the token
        char currentChar = checkNextChar();

        // if the current character is a letter, either keyword or ID
        if(Character.isLetter(currentChar)){
           String keywordOrId = buildKeywordOrId();
           if(keywords.containsKey(keywordOrId)){
               currentToken = keywords.get(keywordOrId);
           } else {
               currentToken = Core.ID;
               currentIDString = keywordOrId;
           }
           return;
        }

        // if the current character is a digit, build a constant and check if it is in range
        if(Character.isDigit(currentChar)){
            int constValue = buildConst();
            if(constValue <0 || constValue > 1000003){
                currentToken = Core.ERROR;
                System.out.println("Error constant out of range");
            }else{
                currentToken = Core.CONST;
                currentConstValue = constValue;
            }
            return;
        }

        // if the current character is a single quote, try to build a string
        if ('\'' == currentChar){
            consumeChar();
            sBuilder.setLength(0);
            while( !eof && ('\'' != checkNextChar())){
                sBuilder.append(consumeChar());
            }
            if (eof) {
                System.out.println("Error unterminated string");
                currentToken = Core.ERROR;
            }else{
                consumeChar();
                currentToken = Core.STRING;
                currentString = sBuilder.toString();
            }
            return;
        }
        
        // Handle the equal sign and double equal sign (assignment and equality)
        if(currentChar == '='){
            consumeChar();
            if(checkNextChar() == '='){
                currentToken = Core.EQUAL;
                consumeChar();
            }else{
                currentToken = Core.ASSIGN;
            }
            return;
        }

        // Handle any symbols
        if(symbols.containsKey(currentChar)){
            currentToken = symbols.get(currentChar);
            consumeChar();
            return;
        }
        

        // If we reach this point, we have an unknown character
        System.out.println("Error unknown character \'"+ currentChar+"\' in file.");
        currentToken = Core.ERROR;
        consumeChar();
    }

    // Return the current token
    public Core currentToken() {
        if (currentToken == null) {
            nextToken();
        }
        return currentToken;
    }

	// Return the identifier string
    public String getId() {
        if(currentToken == Core.ID){
            return currentIDString;
        }else{
            System.out.println("Error not an ID");
            return null;
        }
    }

	// Return the constant value
    public int getConst() {
        if(currentToken == Core.CONST){
            return currentConstValue;
        }else{
            System.out.println("Error not a CONST");
            return -1;
        }    
    }
	
	// Return the character string
    public String getString() {
        if(currentToken == Core.STRING){
            return currentString;
        }else{
            System.out.println("Error not a STRING");
            return null;
        }
        
    }

    // Read in the next character
    private void moveToNextChar() {
        try {
            nextChar = bReader.read();
            if (nextChar == -1) {
                eof = true;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
            return;
        }
    }
    
    // Check the next character without consuming it
    private char checkNextChar(){
        //If we are at the end of the file return null character
        if(eof){
            return '\0';
        }
        return (char) nextChar;
    }
    
    // Consume the character after parsed, and move to the next character
    private char consumeChar(){
        char c = (char) nextChar;
        moveToNextChar();
        return c;
    }

    // Consume any whitespace available between tokens
    private void consumeWhitespace() {
        while ( !eof && Character.isWhitespace(checkNextChar())) {
            moveToNextChar();
        }
    }

    // Build a keyword or ID and return
    private String buildKeywordOrId(){
        sBuilder.setLength(0);
        while(Character.isLetterOrDigit(checkNextChar())){
            sBuilder.append(consumeChar());
        }
        return sBuilder.toString();
    }

    // Build a constant and return
    private int buildConst(){
        sBuilder.setLength(0);
        while(Character.isDigit(checkNextChar())){
            sBuilder.append(consumeChar());
        }
        return Integer.parseInt(sBuilder.toString());
    }

    // Initialize the keywords and symbols (Except for equal which is handled in nextToken)
    private void initKeywordsAndSymbols(){
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


}
