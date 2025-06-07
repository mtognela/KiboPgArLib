package com.kibo.pgar.lib.Types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.charset.Charset;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Deque;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.regex.Matcher;
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
 * Uses TypeToken pattern to overcome Java's type erasure limitations and null
 * pointers
 * 
 * @author Mattia Tognela (mtognela)
 * @version 1.4
 */
public class TypeSafe {

    /**
     * Map of type handlers that provide default empty values for supported types.
     * Contains predefined handlers for common Java types including primitives,
     * collections, date/time types, and more.
     */
    private static Map<TypeToken<?>, Supplier<?>> typeHandlers = new HashMap<>();

    static {
        // Primitive wrapper types
        typeHandlers.put(new TypeToken<Boolean>() {}, () -> Boolean.FALSE);
        typeHandlers.put(new TypeToken<Byte>() {}, () -> (byte) 0);
        typeHandlers.put(new TypeToken<Short>() {}, () -> (short) 0);
        typeHandlers.put(new TypeToken<Integer>() {}, () -> 0);
        typeHandlers.put(new TypeToken<Long>() {}, () -> 0L);
        typeHandlers.put(new TypeToken<Float>() {}, () -> 0.0f);
        typeHandlers.put(new TypeToken<Double>() {}, () -> 0.0);
        typeHandlers.put(new TypeToken<Character>() {}, () -> '\0');

        // String and StringBuilder types
        typeHandlers.put(new TypeToken<String>() {}, () -> "");
        typeHandlers.put(new TypeToken<StringBuilder>() {}, StringBuilder::new);
        typeHandlers.put(new TypeToken<StringBuffer>() {}, StringBuffer::new);

        // Optional types
        typeHandlers.put(new TypeToken<Optional>() {}, Optional::empty);
        typeHandlers.put(new TypeToken<OptionalInt>() {}, OptionalInt::empty);
        typeHandlers.put(new TypeToken<OptionalLong>() {}, OptionalLong::empty);
        typeHandlers.put(new TypeToken<OptionalDouble>() {}, OptionalDouble::empty);

        // Date and Time types
        typeHandlers.put(new TypeToken<LocalDate>() {}, () -> LocalDate.MIN);
        typeHandlers.put(new TypeToken<LocalDateTime>() {}, () -> LocalDateTime.MIN);
        typeHandlers.put(new TypeToken<LocalTime>() {}, () -> LocalTime.MIN);
        typeHandlers.put(new TypeToken<Instant>() {}, () -> Instant.EPOCH);
        typeHandlers.put(new TypeToken<Duration>() {}, () -> Duration.ZERO);
        typeHandlers.put(new TypeToken<Period>() {}, () -> Period.ZERO);
        typeHandlers.put(new TypeToken<ZonedDateTime>() {}, () -> ZonedDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC));
        typeHandlers.put(new TypeToken<OffsetDateTime>() {},
                () -> OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC));
        typeHandlers.put(new TypeToken<OffsetTime>() {}, () -> OffsetTime.of(LocalTime.MIN, ZoneOffset.UTC));
        typeHandlers.put(new TypeToken<Year>() {}, () -> Year.of(1));
        typeHandlers.put(new TypeToken<YearMonth>() {}, () -> YearMonth.of(1, 1));
        typeHandlers.put(new TypeToken<MonthDay>() {}, () -> MonthDay.of(1, 1));
        typeHandlers.put(new TypeToken<ZoneId>() {}, () -> ZoneOffset.UTC);
        typeHandlers.put(new TypeToken<ZoneOffset>() {}, () -> ZoneOffset.UTC);

        // Numeric types
        typeHandlers.put(new TypeToken<BigDecimal>() {}, () -> BigDecimal.ZERO);
        typeHandlers.put(new TypeToken<BigInteger>() {}, () -> BigInteger.ZERO);
        typeHandlers.put(new TypeToken<Number>() {}, () -> 0);

        // Atomic types
        typeHandlers.put(new TypeToken<AtomicInteger>() {}, () -> new AtomicInteger(0));
        typeHandlers.put(new TypeToken<AtomicLong>() {}, () -> new AtomicLong(0L));
        typeHandlers.put(new TypeToken<AtomicBoolean>() {}, () -> new AtomicBoolean(false));
        typeHandlers.put(new TypeToken<AtomicReference>() {}, () -> new AtomicReference<>());

        // UUID and Path
        typeHandlers.put(new TypeToken<UUID>() {}, () -> new UUID(0L, 0L));
        typeHandlers.put(new TypeToken<Path>() {}, () -> Path.of(""));
        typeHandlers.put(new TypeToken<File>() {}, () -> new File(""));

        // Collection types
        typeHandlers.put(new TypeToken<List>() {}, Collections::emptyList);
        typeHandlers.put(new TypeToken<Set>() {}, Collections::emptySet);
        typeHandlers.put(new TypeToken<Map>() {}, Collections::emptyMap);
        typeHandlers.put(new TypeToken<Collection>() {}, Collections::emptyList);
        typeHandlers.put(new TypeToken<Queue>() {}, () -> new ArrayDeque<>());
        typeHandlers.put(new TypeToken<Deque>() {}, () -> new ArrayDeque<>());
        typeHandlers.put(new TypeToken<Stack>() {}, Stack::new);
        typeHandlers.put(new TypeToken<Vector>() {}, Vector::new);
        typeHandlers.put(new TypeToken<ArrayList>() {}, ArrayList::new);
        typeHandlers.put(new TypeToken<LinkedList>() {}, LinkedList::new);
        typeHandlers.put(new TypeToken<HashSet>() {}, HashSet::new);
        typeHandlers.put(new TypeToken<LinkedHashSet>() {}, LinkedHashSet::new);
        typeHandlers.put(new TypeToken<TreeSet>() {}, TreeSet::new);
        typeHandlers.put(new TypeToken<HashMap>() {}, HashMap::new);
        typeHandlers.put(new TypeToken<LinkedHashMap>() {}, LinkedHashMap::new);
        typeHandlers.put(new TypeToken<TreeMap>() {}, TreeMap::new);
        typeHandlers.put(new TypeToken<ConcurrentHashMap>() {}, ConcurrentHashMap::new);
        typeHandlers.put(new TypeToken<Properties>() {}, Properties::new);

        // Stream types
        typeHandlers.put(new TypeToken<Stream>() {}, Stream::empty);
        typeHandlers.put(new TypeToken<IntStream>() {}, IntStream::empty);
        typeHandlers.put(new TypeToken<LongStream>() {}, LongStream::empty);
        typeHandlers.put(new TypeToken<DoubleStream>() {}, DoubleStream::empty);

        // Regex and Pattern
        typeHandlers.put(new TypeToken<Pattern>() {}, () -> Pattern.compile(""));
        typeHandlers.put(new TypeToken<Matcher>() {}, () -> Pattern.compile("").matcher(""));

        // Locale and Currency
        typeHandlers.put(new TypeToken<Locale>() {}, Locale::getDefault);
        typeHandlers.put(new TypeToken<Currency>() {}, () -> Currency.getInstance("USD"));

        // Thread and Executor types
        typeHandlers.put(new TypeToken<Thread>() {}, () -> new Thread(() -> {
        }));
        typeHandlers.put(new TypeToken<ThreadLocal>() {}, ThreadLocal::new);
        typeHandlers.put(new TypeToken<ExecutorService>() {}, () -> Executors.newSingleThreadExecutor());
        typeHandlers.put(new TypeToken<CompletableFuture>() {}, CompletableFuture::new);
        typeHandlers.put(new TypeToken<Future>() {}, () -> CompletableFuture.completedFuture(null));

        // Random and Security
        typeHandlers.put(new TypeToken<Random>() {}, Random::new);
        typeHandlers.put(new TypeToken<SecureRandom>() {}, SecureRandom::new);

        // IO types
        typeHandlers.put(new TypeToken<ByteArrayInputStream>() {}, () -> new ByteArrayInputStream(new byte[0]));
        typeHandlers.put(new TypeToken<ByteArrayOutputStream>() {}, ByteArrayOutputStream::new);
        typeHandlers.put(new TypeToken<StringReader>() {}, () -> new StringReader(""));
        typeHandlers.put(new TypeToken<StringWriter>() {}, StringWriter::new);
        typeHandlers.put(new TypeToken<PrintWriter>() {}, () -> new PrintWriter(new StringWriter()));
        typeHandlers.put(new TypeToken<Scanner>() {}, () -> new Scanner(""));

        // Reflection types
        typeHandlers.put(new TypeToken<Class>() {}, () -> Object.class);

        // Exception types
        typeHandlers.put(new TypeToken<Exception>() {}, Exception::new);
        typeHandlers.put(new TypeToken<RuntimeException>() {}, RuntimeException::new);
        typeHandlers.put(new TypeToken<IllegalArgumentException>() {}, IllegalArgumentException::new);
        typeHandlers.put(new TypeToken<NullPointerException>() {}, NullPointerException::new);

        // Enum support (generic)
        typeHandlers.put(new TypeToken<Enum>() {}, () -> null); // Enums need special handling

        // Buffer types
        typeHandlers.put(new TypeToken<Buffer>() {}, () -> ByteBuffer.allocate(0));
        typeHandlers.put(new TypeToken<ByteBuffer>() {}, () -> ByteBuffer.allocate(0));
        typeHandlers.put(new TypeToken<CharBuffer>() {}, () -> CharBuffer.allocate(0));
        typeHandlers.put(new TypeToken<IntBuffer>() {}, () -> IntBuffer.allocate(0));
        typeHandlers.put(new TypeToken<LongBuffer>() {}, () -> LongBuffer.allocate(0));
        typeHandlers.put(new TypeToken<FloatBuffer>() {}, () -> FloatBuffer.allocate(0));
        typeHandlers.put(new TypeToken<DoubleBuffer>() {}, () -> DoubleBuffer.allocate(0));

        // Charset and Encoding
        typeHandlers.put(new TypeToken<Charset>() {}, () -> StandardCharsets.UTF_8);

        // Formatting
        typeHandlers.put(new TypeToken<Formatter>() {}, Formatter::new);
        typeHandlers.put(new TypeToken<DecimalFormat>() {}, DecimalFormat::new);
        typeHandlers.put(new TypeToken<SimpleDateFormat>() {}, SimpleDateFormat::new);
    }

    /**
     * Gets an empty/default instance for the specified TypeToken.
     * The instance is cached for future use.
     * 
     * @param <T>       The type of the empty instance to return
     * @param typeToken The TypeToken representing the desired type
     * @return An empty/default instance of the specified type
     * @throws IllegalArgumentException if the type is not supported
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEmpty(TypeToken<T> typeToken) {
        Supplier<?> handler = typeHandlers.get(typeToken);

        if (handler != null)
            return (T) handler.get();
        else
            throw new IllegalArgumentException(
                    PrettyStrings.prettify(AnsiColors.RED, AnsiWeights.BOLD, null,
                            "Unsupported type: %s", typeToken.toString()));

    }

    /**
     * Registers a custom handler for providing empty/default instances of a type.
     * 
     * If a handler already exists for the type, it will not be replaced.
     * 
     * @param <T>             The type to register a handler for
     * @param typeElement     The TypeToken representing the type
     * @param defaultSupplier The Supplier that provides empty/default instances
     */
    public static <T> void defineEmpty(TypeToken<T> typeElement, Supplier<? extends T> defaultSupplier) {
        typeHandlers.putIfAbsent(typeElement, defaultSupplier);
    }

    /**
     * Registers a custom handler for providing empty/default instances of a type.
     * 
     * If a handler already exists for the type, it will not be replaced.
     * 
     * @param <T>          The type to register a handler for
     * @param typeElement  The TypeToken representing the type
     * @param defaultValue The default value to return
     */
    public static <T> void defineEmpty(TypeToken<T> typeElement, T defaultValue) {
        typeHandlers.putIfAbsent(typeElement, () -> defaultValue);
    }

}