package com.kibo.pgar.lib.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.reflect.Field;

class AnyCollectionsTest {

    @BeforeEach
    void clearCache() throws Exception {
        // Clear the static cache before each test
        Field field = AnyCollections.class.getDeclaredField("allEmptyCollections");
        field.setAccessible(true);
        ((java.util.Map<?, ?>) field.get(null)).clear();
    }

    @Nested
    @DisplayName("Basic Functionality Tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should return String instance, not Class")
        void testStringType() {
            String result = AnyCollections.emptyInstanceForAnyType(String.class);
            
            assertNotNull(result, "Result should not be null");
            assertEquals("", result, "Should return empty string instance");
            assertTrue(result instanceof String, "Should return String instance");
            System.out.println("String test result: " + result + " (class: " + result.getClass() + ")");
        }

        @Test
        @DisplayName("Should return ArrayList instance, not Class")
        void testArrayListType() {
            ArrayList<Object> result = AnyCollections.emptyInstanceForAnyType(ArrayList.class);
            
            assertNotNull(result, "Result should not be null");
            assertTrue(result instanceof ArrayList, "Should return ArrayList instance");
            assertEquals(0, result.size(), "Should return empty ArrayList");
            System.out.println("ArrayList test result: " + result + " (class: " + result.getClass() + ")");
        }

        @Test
        @DisplayName("Should return HashMap instance, not Class")
        void testHashMapType() {
            HashMap<Object, Object> result = AnyCollections.emptyInstanceForAnyType(HashMap.class);
            
            assertNotNull(result, "Result should not be null");
            assertTrue(result instanceof HashMap, "Should return HashMap instance");
            assertEquals(0, result.size(), "Should return empty HashMap");
            System.out.println("HashMap test result: " + result + " (class: " + result.getClass() + ")");
        }
    }

    @Nested
    @DisplayName("Caching Behavior Tests")
    class CachingTests {

        @Test
        @DisplayName("Should return same instance for multiple calls with same type")
        void testCachingBehavior() {
            HashSet<Object> result1 = AnyCollections.emptyInstanceForAnyType(HashSet.class);
            HashSet<Object> result2 = AnyCollections.emptyInstanceForAnyType(HashSet.class);
            
            assertNotNull(result1, "First result should not be null");
            assertNotNull(result2, "Second result should not be null");
            
            // Should be the exact same instance due to caching
            assertSame(result1, result2, "Results should be the same instance due to caching");
            System.out.println("Caching test - Same reference: " + (result1 == result2));
        }

        @Test
        @DisplayName("Should cache different types separately")
        void testDifferentTypeCaching() {
            String stringResult = AnyCollections.emptyInstanceForAnyType(String.class);
            StringBuilder sbResult = AnyCollections.emptyInstanceForAnyType(StringBuilder.class);
            
            assertNotNull(stringResult, "String result should not be null");
            assertNotNull(sbResult, "StringBuilder result should not be null");
            
            // Different types should have different instances
            assertNotSame(stringResult, sbResult, "Different types should have different instances");
            assertTrue(stringResult instanceof String, "String result should be String");
            assertTrue(sbResult instanceof StringBuilder, "StringBuilder result should be StringBuilder");
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle class without default constructor")
        void testNoDefaultConstructor() {
            TestClassNoDefaultConstructor result = 
                AnyCollections.emptyInstanceForAnyType(TestClassNoDefaultConstructor.class);
            
            // Should return null when constructor fails
            assertNull(result, "Should return null for class without default constructor");
        }

        @Test
        @DisplayName("Should handle abstract class")
        void testAbstractClass() {
            AbstractTestClass result = 
                AnyCollections.emptyInstanceForAnyType(AbstractTestClass.class);
            
            // Should return null when instantiation fails
            assertNull(result, "Should return null for abstract class");
        }

        @Test
        @DisplayName("Should handle wrapper classes without default constructors")
        void testWrapperClasses() {
            // Integer, Long, Double, etc. don't have public default constructors
            Integer intResult = AnyCollections.emptyInstanceForAnyType(Integer.class);
            Long longResult = AnyCollections.emptyInstanceForAnyType(Long.class);
            Double doubleResult = AnyCollections.emptyInstanceForAnyType(Double.class);
            
            assertNull(intResult, "Integer should return null (no default constructor)");
            assertNull(longResult, "Long should return null (no default constructor)");
            assertNull(doubleResult, "Double should return null (no default constructor)");
        }
    }

    @Nested
    @DisplayName("SafeCastToClass Method Tests")
    class SafeCastToClassTests {

        @Test
        @DisplayName("Should successfully cast Class object to parameterized Class")
        void testValidClassCasting() {
            Object classObj = String.class;
            Class<String> result = AnyCollections.safeCastToClass(classObj, String.class);
            
            assertNotNull(result, "Should successfully cast valid Class object");
            assertEquals(String.class, result, "Should return the correct Class");
        }

        @Test
        @DisplayName("Should return null for non-Class objects")
        void testNonClassObject() {
            Object nonClassObj = "Not a class";
            Class<String> result = AnyCollections.safeCastToClass(nonClassObj, String.class);
            
            assertNull(result, "Should return null for non-Class objects");
        }

        @Test
        @DisplayName("Should return null for incompatible types")
        void testIncompatibleTypes() {
            Object intClassObj = Integer.class;
            Class<String> result = AnyCollections.safeCastToClass(intClassObj, String.class);
            
            assertNull(result, "Should return null for incompatible types");
        }

        @Test
        @DisplayName("Should handle inheritance hierarchy correctly")
        void testInheritanceHierarchy() {
            // Object.class can be assigned from String.class (Object is superclass of String)
            Object stringClassObj = String.class;
            Class<Object> result = AnyCollections.safeCastToClass(stringClassObj, Object.class);
            
            assertNotNull(result, "Should handle inheritance correctly");
            assertEquals(String.class, result, "Should return String.class");
        }

        @Test
        @DisplayName("Should handle null input")
        void testNullInput() {
            Class<String> result = AnyCollections.safeCastToClass(null, String.class);
            
            assertNull(result, "Should return null for null input");
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should work with multiple different collection types")
        void testMultipleCollectionTypes() {
            ArrayList<Object> listResult = AnyCollections.emptyInstanceForAnyType(ArrayList.class);
            HashMap<Object, Object> mapResult = AnyCollections.emptyInstanceForAnyType(HashMap.class);
            HashSet<Object> setResult = AnyCollections.emptyInstanceForAnyType(HashSet.class);
            
            assertNotNull(listResult, "ArrayList result should not be null");
            assertNotNull(mapResult, "HashMap result should not be null");
            assertNotNull(setResult, "HashSet result should not be null");
            
            // All should be different instances
            assertNotSame(listResult, mapResult, "Different types should have different instances");
            assertNotSame(mapResult, setResult, "Different types should have different instances");
            assertNotSame(listResult, setResult, "Different types should have different instances");
            
            // All should be correct types
            assertTrue(listResult instanceof ArrayList, "Should be ArrayList instance");
            assertTrue(mapResult instanceof HashMap, "Should be HashMap instance");
            assertTrue(setResult instanceof HashSet, "Should be HashSet instance");
        }

        @Test
        @DisplayName("Should maintain cache across different method calls")
        void testCacheConsistency() {
            StringBuilder sb1 = AnyCollections.emptyInstanceForAnyType(StringBuilder.class);
            StringBuilder sb2 = AnyCollections.emptyInstanceForAnyType(StringBuilder.class);
            
            assertSame(sb1, sb2, "Same type should return same cached instance");
            
            // Modify one instance
            sb1.append("test");
            
            // Both should be modified since they're the same instance
            assertEquals("test", sb2.toString(), "Both references should point to same instance");
        }
    }

    // Helper classes for testing
    private static class TestClassNoDefaultConstructor {
        
    }

    private static abstract class AbstractTestClass {
        // Abstract class cannot be instantiated
    }
}