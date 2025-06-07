package com.kibo.pgar.lib.Types;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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
    private static final Map<TypeT<?>, Object> emptyCache = new HashMap<>();
    
    /**
     * Gets empty collection for the specified TypeToken
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEmpty(TypeT<T> typeToken) {
        return (T) emptyCache.computeIfAbsent(typeToken,
                (Function<? super TypeT<?>, ? extends Object>) TypeSafeEmpty.createEmpty(typeToken));
    }

    /**
     * Creates appropriate empty collection based on the type
     */
    private static Object createEmpty(TypeT<?> typeToken) {
        Class<?> rawType = extractRawType(typeToken.getType());

        return switch (rawType.getSimpleName()) {
            case LIST -> Collections.emptyList();
            case SET -> Collections.emptySet();
            case MAP -> Collections.emptyMap();
            case COLLECTION -> Collections.emptyList();
            default -> null;
        };
    }

    private static Class<?> extractRawType(Type type) {
        if (type instanceof Class) 
            return (Class<?>) type;
        else
            throw new IllegalArgumentException(PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null,
                "Cannot extract raw type from: %s", type.toString()));
    }

    /**
     * Convenience method for creating TypeTokens
     */
    public static <T> TypeT<T> typeToken() {
        return new TypeT<T>() {};
    }
    
    public static void clearCache() {
        emptyCache.clear();
    }
    
    public static int getCacheSize() {
        return emptyCache.size();
    }
    
    public static Set<String> getCachedTypes() {
        return emptyCache.keySet().stream()
                .map(TypeT::toString)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    } 
    
}