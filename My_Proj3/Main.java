import java.io.*;

class Main {
    public static void main(String[] args) {
        // Ensure correct number of arguments
        if (args.length != 2) {
            System.out.println("ERROR: Usage - java Main <code file> <data file>");
            return;
        }

        // Initialize the scanner with the code file
        Scanner scanner = new Scanner(args[0]);
        Parser.scanner = scanner;

        // Parse the input program
        Procedure procedure = new Procedure();
        procedure.parse();

        // Execute the parsed program
        procedure.execute();
    }
}
