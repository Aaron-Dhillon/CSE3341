import java.util.*;

class Variable {
    private boolean isInteger;
    private Integer intValue;
    Map<String, Integer> objectValue;
    String defaultKey;

    // Constructor for integer variables (default value 0)
    public Variable(boolean isInteger) {
        this.isInteger = isInteger;
        if (isInteger) {
            this.intValue = 0; // Default integer value
            this.objectValue = null;
        } else {
            this.intValue = null;
            this.objectValue = null; // Will be initialized later
        }
    }

    // Check if this variable holds an integer
    public boolean isInteger() {
        return isInteger;
    }

    // Check if this variable holds an object
    public boolean isObject() {
        return !isInteger;
    }

    // Get integer value
    public int getIntValue() {
        if (!isInteger) {
            objectValue.get(defaultKey);
        }
        return intValue;
    }

    // Set integer value
    public void setIntValue(int value) {
        if (!isInteger) {
            objectValue.put(defaultKey, intValue);
        }
        this.intValue = value;
    }

    // Initialize as an object (when assigned `id = new object(string, expr)`)
    public void initializeAsObject(String key, int initialValue) {
        if (isInteger) {
            System.out.println("ERROR: Variable is not an object.");
            System.exit(1);
        }
        if (this.objectValue != null) {
            System.out.println("ERROR: Object already initialized.");
            System.exit(1);
        }
        this.objectValue = new HashMap<>();
        this.defaultKey = key;
        setObjectKey(key, initialValue);
    }

    // Set a key-value pair in an object
    public void setObjectKey(String key, int value) {
        objectValue.put(key, value);
    }

    // Get a value from an object's key
    public int getObjectKey(String key) {
        if (!objectValue.containsKey(key)) {
            System.out.println("ERROR: Key '" + key + "' does not exist in object.");
            System.exit(1);
        }
        return objectValue.get(key);
    }

    // Aliasing: Make this variable reference another object's map
    public void aliasTo(Variable other) {
        this.objectValue = other.objectValue; // Now both variables reference the same object
    }

    // Print the map
    public void print() {
        objectValue.forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
