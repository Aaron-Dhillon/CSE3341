
Aaron Dhillon

=================================

Project Overview
------------------
This project implements an interpreter for the Core programming language. The interpreter consists of a scanner, parser, and execution components, which work together to process Core programs. The project follows a recursive descent approach for parsing and executes statements in a structured manner.

Files
------
- **Main.java**: Entry point for running the interpreter.
- **Scanner.java**: Reads input and tokenizes the Core program.
- **Parser.java**: Implements recursive descent parsing to build an abstract syntax tree.
- **Memory.java**: Manages variable storage across different scopes and reads input from a data file.
- **Variable.java**: Represents integer and object variables.
- **StmtSeq.java**: Handles sequences of statements.
- **Stmt.java**: Base interface for all statements.
- **Assign.java**: Implements assignment statements.
- **If.java**: Handles conditional statements with correct scope handling.
- **Loop.java**: Implements for loops with correct scope handling.
- **Read.java**: Reads integer input and assigns it to variables.
- **Print.java**: Outputs variable values.
- **Expr.java**: Evaluates mathematical expressions.
- **Factor.java**: Handles individual terms in expressions.
- **Term.java**: Supports multiplication and division operations.
- **Cond.java**: Handles conditional expressions.
- **Decl.java**: Represents declarations of variables and objects.
- **DeclInteger.java**: Manages integer variable declarations.
- **DeclObj.java**: Manages object variable declarations.
- **DeclSeq.java**: Manages sequences of declarations.
- **Cmpr.java**: Implements comparison expressions.
- **Procedure.java**: Handles procedure declarations and calls.
- **Core.java**: Defines the Core language structure.
- **Id.java**: Represents identifiers used in expressions and assignments.

Design Approach
------------------
The interpreter implements a structured approach to variable handling using **Memory.java**:

### Global & Local Scope Management:
- Variables are stored in a **global memory map** for top-level declarations and a **stack-based structure (localMemoryStack)** for nested scopes.

### Entering a Scope:
- When execution enters a new block (such as a loop or procedure), a new local scope is pushed onto the stack.

### Exiting a Scope:
- Upon leaving a block, the most recent local scope is popped, ensuring that variables declared in the block are no longer accessible.

### Variable Lookup:
- When retrieving a variable, the interpreter searches from the **most recent local scope outward** to the global memory.

### Variable Updates:
- Assignments first check if the variable exists in the local scope before modifying the global memory.

Testing
---------
For testing, I used the provided test cases along with some additional test cases I created for edge cases. I also utilized test cases that were uploaded to Piazza by other students.

Known Issues
--------------
No known remaining bugs.

