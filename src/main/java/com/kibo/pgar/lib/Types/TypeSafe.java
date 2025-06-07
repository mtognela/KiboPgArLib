package com.kibo.pgar.lib.Types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import com.kibo.pgar.lib.Formats.AnsiColors;
import com.kibo.pgar.lib.Formats.AnsiWeights;
import com.kibo.pgar.lib.Strings.PrettyStrings;

/**
 * Type-safe empty collection cache that preserves generic type information
 * Uses TypeToken pattern to overcome Java's type erasure limitations and null pointers 
 * 
 * @author Mattia Tognela (mtognela)
 * @version 1.3
 */
public class TypeSafe {

    /**
     * Map of type handlers that provide default empty values for supported types.
     * Contains predefined handlers for common Java types including primitives,
     * collections, date/time types, and more.
     */
    private static Map<String, Supplier<?>> typeHandlers = new HashMap<>(
        Map.ofEntries(
            Map.entry("boolean", () -> Boolean.FALSE),
            Map.entry("byte", () -> (byte) 0),
            Map.entry("short", () -> (short) 0),
            Map.entry("int", () -> 0),
            Map.entry("long", () -> 0L),
            Map.entry("float", () -> 0.0f),
            Map.entry("double", () -> 0.0),
            Map.entry("char", () -> '\0'),

            // Primitive wrapper types
            Map.entry("Boolean", () -> Boolean.FALSE),
            Map.entry("Byte", () -> (byte) 0),
            Map.entry("Short", () -> (short) 0),
            Map.entry("Integer", () -> 0),
            Map.entry("Long", () -> 0L),
            Map.entry("Float", () -> 0.0f),
            Map.entry("Double", () -> 0.0),
            Map.entry("Character", () -> '\0'),

            // String and StringBuilder types
            Map.entry("String", () -> ""),
            Map.entry("StringBuilder", StringBuilder::new),
            Map.entry("StringBuffer", StringBuffer::new),

            // Optional types
            Map.entry("Optional", Optional::empty),
            Map.entry("OptionalInt", OptionalInt::empty),
            Map.entry("OptionalLong", OptionalLong::empty),
            Map.entry("OptionalDouble", OptionalDouble::empty),

            // Date and Time types
            Map.entry("LocalDate", () -> LocalDate.MIN),
            Map.entry("LocalDateTime", () -> LocalDateTime.MIN),
            Map.entry("LocalTime", () -> LocalTime.MIN),
            Map.entry("Instant", () -> Instant.EPOCH),
            Map.entry("Duration", () -> Duration.ZERO),
            Map.entry("Period", () -> Period.ZERO),
            Map.entry("ZonedDateTime", () -> ZonedDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC)),
            Map.entry("OffsetDateTime", () -> OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC)),
            Map.entry("OffsetTime", () -> OffsetTime.of(LocalTime.MIN, ZoneOffset.UTC)),
            Map.entry("Year", () -> Year.of(1)),
            Map.entry("YearMonth", () -> YearMonth.of(1, 1)),
            Map.entry("MonthDay", () -> MonthDay.of(1, 1)),
            Map.entry("ZoneId", () -> ZoneOffset.UTC),
            Map.entry("ZoneOffset", () -> ZoneOffset.UTC),

            // Numeric types
            Map.entry("BigDecimal", () -> BigDecimal.ZERO),
            Map.entry("BigInteger", () -> BigInteger.ZERO),
            Map.entry("Number", () -> 0),

            // Atomic types
            Map.entry("AtomicInteger", () -> new AtomicInteger(0)),
            Map.entry("AtomicLong", () -> new AtomicLong(0L)),
            Map.entry("AtomicBoolean", () -> new AtomicBoolean(false)),
            Map.entry("AtomicReference", () -> new AtomicReference<>()),

            // UUID and Path
            Map.entry("UUID", () -> new UUID(0L, 0L)),
            Map.entry("Path", () -> Path.of("")),
            Map.entry("File", () -> new File("")),

            // Collection types (handled separately but included for completeness)
            Map.entry("List", Collections::emptyList),
            Map.entry("Set", Collections::emptySet),
            Map.entry("Map", Collections::emptyMap),
            Map.entry("Collection", Collections::emptyList),
            Map.entry("Queue", () -> new ArrayDeque<>()),
            Map.entry("Deque", () -> new ArrayDeque<>()),
            Map.entry("Stack", Stack::new),
            Map.entry("Vector", Vector::new),
            Map.entry("ArrayList", ArrayList::new),
            Map.entry("LinkedList", LinkedList::new),
            Map.entry("HashSet", HashSet::new),
            Map.entry("LinkedHashSet", LinkedHashSet::new),
            Map.entry("TreeSet", TreeSet::new),
            Map.entry("HashMap", HashMap::new),
            Map.entry("LinkedHashMap", LinkedHashMap::new),
            Map.entry("TreeMap", TreeMap::new),
            Map.entry("ConcurrentHashMap", ConcurrentHashMap::new),
            Map.entry("Properties", Properties::new),

            // Stream types
            Map.entry("Stream", Stream::empty),
            Map.entry("IntStream", IntStream::empty),
            Map.entry("LongStream", LongStream::empty),
            Map.entry("DoubleStream", DoubleStream::empty),

            // Regex and Pattern
            Map.entry("Pattern", () -> Pattern.compile("")),
            Map.entry("Matcher", () -> Pattern.compile("").matcher("")),

            // Locale and Currency
            Map.entry("Locale", Locale::getDefault),
            Map.entry("Currency", () -> Currency.getInstance("USD")),
        

            // Thread and Executor types
            Map.entry("Thread", () -> new Thread(() -> {
            })),
            Map.entry("ThreadLocal", ThreadLocal::new),
            Map.entry("ExecutorService", () -> Executors.newSingleThreadExecutor()),
            Map.entry("CompletableFuture", CompletableFuture::new),
            Map.entry("Future", () -> CompletableFuture.completedFuture(null)),

            // Random and Security
            Map.entry("Random", Random::new),
            Map.entry("SecureRandom", SecureRandom::new),

            // IO types
            Map.entry("ByteArrayInputStream", () -> new ByteArrayInputStream(new byte[0])),
            Map.entry("ByteArrayOutputStream", ByteArrayOutputStream::new),
            Map.entry("StringReader", () -> new StringReader("")),
            Map.entry("StringWriter", StringWriter::new),
            Map.entry("PrintWriter", () -> new PrintWriter(new StringWriter())),
            Map.entry("Scanner", () -> new Scanner("")),

            // Reflection types
            Map.entry("Class", () -> Object.class),

            // Exception types
            Map.entry("Exception", Exception::new),
            Map.entry("RuntimeException", RuntimeException::new),
            Map.entry("IllegalArgumentException", IllegalArgumentException::new),
            Map.entry("NullPointerException", NullPointerException::new),

            // Enum support (generic)
            Map.entry("Enum", () -> null), // Enums need special handling

            // Buffer types
            Map.entry("Buffer", () -> ByteBuffer.allocate(0)),
            Map.entry("ByteBuffer", () -> ByteBuffer.allocate(0)),
            Map.entry("CharBuffer", () -> CharBuffer.allocate(0)),
            Map.entry("IntBuffer", () -> IntBuffer.allocate(0)),
            Map.entry("LongBuffer", () -> LongBuffer.allocate(0)),
            Map.entry("FloatBuffer", () -> FloatBuffer.allocate(0)),
            Map.entry("DoubleBuffer", () -> DoubleBuffer.allocate(0)),

            // Charset and Encoding
            Map.entry("Charset", () -> StandardCharsets.UTF_8),

            // Formatting
            Map.entry("Formatter", Formatter::new),
            Map.entry("DecimalFormat", DecimalFormat::new),
            Map.entry("SimpleDateFormat", SimpleDateFormat::new)
        ));

    
    /**
     * Gets an empty/default instance for the specified TypeToken.
     * The instance is cached for future use.
     * 
     * @param <T> The type of the empty instance to return
     * @param typeToken The TypeToken representing the desired type
     * @return An empty/default instance of the specified type
     * @throws IllegalArgumentException if the type is not supported
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEmpty(TypeToken<T> typeToken) {
        Class<?> rawType = extractRawType(typeToken.getType());
        String typeName = rawType.getSimpleName();
        Supplier<?> handler = typeHandlers.get(typeName);

        if (handler != null)
            return (T) handler.get();
        else
            throw new IllegalArgumentException(
                    PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null,
                            "Unsupported type: %s", typeName));

    }

    /**
     * Registers a custom handler for providing empty/default instances of a type.
     * If a handler already exists for the type, it will not be replaced.
     * 
     * @param <T> The type to register a handler for
     * @param typeElement The TypeToken representing the type
     * @param defult The Supplier that provides empty/default instances
     */
    public static <T> void defineEmpthy(TypeToken<T> typeElement, Supplier<?> defult) {
        Class<?> rawType = extractRawType(typeElement.getType());
        
        String typeName = rawType.getSimpleName();
        
        typeHandlers.putIfAbsent(typeName, defult);
    }


    /**
     * Extracts the raw type from a Type object, handling both Class and ParameterizedType cases.
     * 
     * @param type The Type to extract from
     * @return The raw Class object
     * @throws IllegalArgumentException if the type cannot be processed
     */

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

}