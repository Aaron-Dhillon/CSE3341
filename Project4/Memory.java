import java.util.*;

class Memory {
	//scanner is stored here as a static field so it is avaiable to the execute method for factor
	public static Scanner data;
	
	// Class and data structures to represent variables
	static class Variable {
		Core type;
		int integerVal;
		String defaultKey;
		Map<String, Integer> mapVal;
		Variable(Core t) {
			this.type = t;
		}
	}
	
	public static HashMap<String, Variable> global;
	public static Stack<HashMap<String, Variable>> local;
	// Store procedures for lookup during calls
	private static HashMap<String, Function> procedures;
	
	// Helper methods to manage memory
	
	// This inializes the global memory structure
	// Called before executing the DeclSeq
	public static void initializeGlobal() {
		global = new HashMap<String, Variable>();
		procedures = new HashMap<String, Function>();
	}
	
	// Register a procedure for later use
	public static void registerFunction(Function func) {
		if (procedures.containsKey(func.name)) {
			System.out.println("ERROR: Duplicate procedure name: " + func.name);
			System.exit(0);
		}
		procedures.put(func.name, func);
	}
	
	// Get a procedure by name
	public static Function getFunction(String name) {
		Function func = procedures.get(name);
		if (func == null) {
			System.out.println("ERROR: Undefined procedure: " + name);
			System.exit(0);
		}
		return func;
	}
	
	// Initializes the local data structure
	// Called before executing the main StmtSeq
	public static void initializeLocal() {
		local = new Stack<HashMap<String, Variable>>();
	}
	
	// Pushes a "scope" for if/loop stmts
	public static void pushScope() {
		local.push(new HashMap<String, Variable>());
	}
	
	// Pops a "scope"
	public static void popScope() {
		local.pop();
	}
	
	// Handles decl integer
	public static void declareInteger(String id) {
		Variable v = new Variable(Core.INTEGER);
		if (local != null && !local.isEmpty()) {
			local.peek().put(id, v);
		} else {
			global.put(id, v);
		}
	}
	
	// Handles decl object
	public static void declareObject(String id) {
		Variable v = new Variable(Core.OBJECT);
		if (local != null && !local.isEmpty()) {
			local.peek().put(id, v);
		} else {
			global.put(id, v);
		}
	}
	
	// Retrives a value from memory (integer or default key value)
	public static int load(String id) {
		int value;
		Variable v = getLocalOrGlobal(id);
		if (v.type == Core.INTEGER) {
			value = v.integerVal;
		} else {
			if (v.mapVal == null) {
				v.mapVal = new HashMap<>();
				v.defaultKey = "default";
				v.mapVal.put(v.defaultKey, 0);
			}
			value = v.mapVal.get(v.defaultKey);
		}
		return value;
	}
	
	// Retrieves a value for the key
	public static int load(String id, String key) {
		Variable v = getLocalOrGlobal(id);
		if (v.mapVal == null) {
			v.mapVal = new HashMap<>();
			v.defaultKey = key;
			v.mapVal.put(key, 0);
		}
		if (!v.mapVal.containsKey(key)) {
			System.out.println("ERROR: key " + key + " is not valid!");
			System.exit(0);
		}
		return v.mapVal.get(key);
	}
	
	// Stores a value (integer or object at default key)
	public static void store(String id, int value) {
		Variable v = getLocalOrGlobal(id);
		if (v.type == Core.INTEGER) {
			v.integerVal = value;
		} else {
			if (v.mapVal == null) {
				v.mapVal = new HashMap<>();
				v.defaultKey = "default";
			}
			v.mapVal.put(v.defaultKey, value);
		}
	}
	
	// Stores a value at key
	public static void store(String id, String key, int value) {
		Variable v = getLocalOrGlobal(id);
		if (v.mapVal == null) {
			v.mapVal = new HashMap<>();
			v.defaultKey = key;
		}
		v.mapVal.put(key, value);
	}
	
	// Handles "new object" assignment
	public static void allocate(String id, String key, int value) {
		Variable v = getLocalOrGlobal(id);
		v.mapVal = new HashMap<>();
		v.defaultKey = key;
		v.mapVal.put(v.defaultKey, value);
	}
	
	// Handles "id : id" assignment
	public static void alias(String lhs, String rhs) {
		Variable v1 = getLocalOrGlobal(lhs);
		Variable v2 = getLocalOrGlobal(rhs);
		v1.mapVal = v2.mapVal;
		v1.defaultKey = v2.defaultKey;
	}
	
	// Looks up value of the variables, searches local then global
	private static Variable getLocalOrGlobal(String id) {
		Variable result = null;
		if (local != null && !local.isEmpty()) {
			Stack<HashMap<String, Variable>> tempStack = new Stack<>();
			while (!local.isEmpty()) {
				HashMap<String, Variable> currentScope = local.pop();
				if (currentScope.containsKey(id)) {
					result = currentScope.get(id);
				}
				tempStack.push(currentScope);
			}
			while (!tempStack.isEmpty()) {
				local.push(tempStack.pop());
			}
		}
		if (result == null) {
			result = global.get(id);
		}
		if (result == null) {
			System.out.println("ERROR: Variable not found: " + id);
			System.exit(0);
		}
		return result;
	}
}