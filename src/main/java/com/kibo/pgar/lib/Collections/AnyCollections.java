package com.kibo.pgar.lib.Collections;

import java.util.HashMap;
import java.util.Map;

public final class AnyCollections {
    private AnyCollections() {}
    
    private static Map<Class<?>, Object> allEmptyCollections = new HashMap<>();
    
    /**
     * Creates and caches empty instances of the specified type.
     * Returns the actual instance of type T, not Class<T>.
     */
    @SuppressWarnings("unchecked")
    public static <T> T emptyInstanceForAnyType(Class<T> type) {
        try {
            // Check cache first
            if (allEmptyCollections.containsKey(type)) {
                return (T) allEmptyCollections.get(type);
            }
            
            // Create new instance
            T instance = type.getDeclaredConstructor().newInstance();
            allEmptyCollections.put(type, instance);
            return instance;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Safely casts an object to a parameterized Class type.
     * This method is for casting Class objects, not instances.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> safeCastToClass(Object obj, Class<T> expectedType) {
        if (!(obj instanceof Class<?>)) {
            return null;
        }
        
        Class<?> actualClass = (Class<?>) obj;
        
        
        if (expectedType.isAssignableFrom(actualClass)) {
            return (Class<T>) actualClass;
        }
        
        return null;
    }
    
}