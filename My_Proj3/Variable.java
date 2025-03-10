import java.util.*;

class Variable {
    // Variables can be either integers or objects

    // flag for integer or object
    private boolean isInteger;

    // Integer value or object value
    private Integer intValue;
    Map<String, Integer> objectValue;

    // Default key for object variables
    String defaultKey;

    // Constructor for integer variables (default value 0)
    public Variable(boolean isInteger) {
        this.isInteger = isInteger;
        if (isInteger) {
            this.intValue = 0; // Default integer value
            this.objectValue = null;
        } else {
            this.intValue = null;
            this.objectValue = null; // Will need to be initialized later
        }
    }

    // Get integer value
    public int getIntValue() {
        if (!isInteger) {
            return objectValue.get(getDefaultKey());
        }
        return intValue;
    }

    // Set integer value
    public void setIntValue(int value) {
        if (!isInteger) {
            if(objectValue == null){
                System.out.println("ERROR: Assignment to null object variable");
                System.exit(1);
            }
            objectValue.put(getDefaultKey(), value);
        }
        this.intValue = value;
    }

    // Initialize as an object (when assigned `id = new object(string, expr)`)
    public void initializeAsObject(String key, int initialValue) {
        if (isInteger) {
            System.out.println("ERROR: Variable is not an object.");
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
        this.objectValue = other.objectValue;
        this.defaultKey = other.getDefaultKey();// Now both variables reference the same object   
    }
    public String getDefaultKey() {
        return defaultKey;
    }
}
