Aaron Dhillon

Files Submitted:
1. Main.java - Program entry point and main execution
2. Core.java - Main interpreter class that handles program execution
3. Parser.java - Handles parsing of the Core language
4. Scanner.java - Lexical analyzer for tokenizing input
5. Function.java - Handles function definitions and implementations
6. Parameters.java - Manages function parameters and their handling
7. Call.java - Implements function call mechanism
8. Assign.java - Handles assignment operations
9. Cmpr.java - Implements comparison operations
10. Cond.java - Handles conditional statements
11. Decl.java - Base class for declarations
12. DeclInteger.java - Integer declaration handling
13. DeclObject.java - Object declaration handling
14. DeclSeq.java - Sequence of declarations
15. Expr.java - Expression handling
16. Factor.java - Factor handling in expressions
17. Id.java - Identifier handling
18. If.java - If statement implementation
19. Loop.java - Loop statement implementation
20. Memory.java - Memory management
21. Print.java - Print statement implementation
22. Procedure.java - Procedure handling
23. Read.java - Read statement implementation
24. Stmt.java - Base statement class
25. StmtSeq.java - Sequence of statements
26. Term.java - Term handling in expressions

Special Features:
No Special Features

Interpreter Design:
The interpreter is implemented using a recursive descent parser.
The call stack is implemented using a Stack data structure. Each function call 
creates a new activation frame that contains:
- Local variables
- Parameter values
- Return address
- Previous frame pointer

The stack grows downward with each function call and shrinks upon return. This allows for:
- Proper function nesting
- Recursive function calls
- Maintaining correct variable scope
- Clean memory management

Testing Methodology:
The interpreter was tested using the provided test cases.

Known Bugs:
No known bugs at this time. All test cases pass successfully.