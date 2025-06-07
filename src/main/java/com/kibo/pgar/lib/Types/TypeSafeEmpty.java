package com.kibo.pgar.lib.Types;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.reflect.TypeToken;
import com.kibo.pgar.lib.Formats.AnsiColors;
import com.kibo.pgar.lib.Formats.AnsiWeights;
import com.kibo.pgar.lib.Strings.PrettyStrings;

/**
 * Type-safe empty collection cache that preserves generic type information
 * Uses TypeToken pattern to overcome Java's type erasure limitations
 */
public class TypeSafeEmpty {
    private static final String COLLECTION = "Collection";
    private static final String MAP = "Map";
    private static final String SET = "Set";
    private static final String LIST = "List";
    private static final Map<TypeToken<?>, Object> emptyCache = new HashMap<>();

    /**
     * Gets empty collection for the specified TypeToken
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEmpty(TypeToken<T> typeToken) {
        return (T) emptyCache.computeIfAbsent(typeToken, TypeSafeEmpty::createEmpty);
    }

    /**
     * Creates appropriate empty collection based on the type
     */
    private static Object createEmpty(TypeToken<?> typeToken) {
        Class<?> rawType = extractRawType(typeToken.getType());

    // Handle primitive types
    if (rawType.isPrimitive()) {
        return switch (rawType.getSimpleName()) {
            case "boolean" -> false;
            case "byte", "short", "int", "long" -> 0;
            case "float" -> 0.0f;
            case "double" -> 0.0;
            case "char" -> '\0';
            default -> throw new IllegalArgumentException(
                PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null, 
                "Unknown primitive type: %s", rawType.toString())
            );
        };
    }

    // Handle common collection types
    if (Collection.class.isAssignableFrom(rawType)) {
        return switch (rawType.getSimpleName()) {
            case LIST -> Collections.emptyList();
            case SET -> Collections.emptySet();
            case COLLECTION -> Collections.emptyList();
            default -> throw new IllegalArgumentException(
                PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null,
                    "Unsupported type: %s", rawType.getSimpleName())
            );
        };
    }

    if (Map.class.isAssignableFrom(rawType)) {
        return Collections.emptyMap();
    }


        return switch (rawType.getSimpleName()) {
            case "String" -> "";
            case "Optional" -> Optional.empty();
            case "LocalDate" -> LocalDate.MIN;
            case "LocalDateTime" -> LocalDateTime.MIN;
            case "Instant" -> Instant.EPOCH;
            case "BigDecimal" -> BigDecimal.ZERO;
            case "BigInteger" -> BigInteger.ZERO;
            case "UUID" -> new UUID(0L, 0L);
            case "AtomicInteger" -> new AtomicInteger(0);
            case "AtomicLong" -> new AtomicLong(0L);
            case "OptionalInt" -> OptionalInt.empty();
            case "OptionalLong" -> OptionalLong.empty();
            case "OptionalDouble" -> OptionalDouble.empty();
            case "Duration" -> Duration.ZERO;
            case "Path" -> Path.of("");
            default -> throw new IllegalArgumentException(
                        PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null,
                                "Unsupported type: %s", rawType.getSimpleName()));
        };
    }

    private static Class<?> extractRawType(Type type) {
        if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof Class) {
            return (Class<?>) type;
        } else {
            throw new IllegalArgumentException(PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null,
                    "Cannot extract raw type from: %s", type.toString()));
        }
    }

    /**
     * Convenience method for creating TypeTokens
     */
    public static <T> TypeToken<T> typeToken() {
        return new TypeToken<T>() {
        };
    }

    public static void clearCache() {
        emptyCache.clear();
    }

    public static int getCacheSize() {
        return emptyCache.size();
    }

    public static Set<String> getCachedTypes() {
        return emptyCache.keySet().stream()
                .map(TypeToken::toString)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    public static void main(String[] args) {
        // Fixed: Use proper generic type
        List<String> stringList = TypeSafeEmpty.getEmpty(new TypeToken<List<String>>() {
        });
        Set<Integer> intSet = TypeSafeEmpty.getEmpty(new TypeToken<Set<Integer>>() {
        });
        Map<String, Object> map = TypeSafeEmpty.getEmpty(new TypeToken<Map<String, Object>>() {
        });

        System.out.println("String list: " + stringList);
        System.out.println("Int set: " + intSet);
        System.out.println("Map: " + map);
        System.out.println("Cache size: " + getCacheSize());
    }
}