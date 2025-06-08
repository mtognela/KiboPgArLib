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

    private static final String UNSUPPORTED_TYPE_S_LOAD_A_DEFAULT_WITH_THE_METHOD_DEFINE_EMPTY = "Unsupported type: %s \nLoad a default with the method defineEmpty";
    private static final String HANDLER_FOR_TYPE_S_RETURNED_INCOMPATIBLE_TYPE_S = "Handler for type %s returned incompatible type %s";
    
    /**
     * Map of type handlers that provide default empty values for supported types.
     * Contains predefined handlers for common Java types including primitives,
     * collections, date/time types, and more.
     */
    private static Map<TypeToken<?>, Supplier<?>> typeHandlers = new HashMap<>();

    static {
        /** Primitive wrapper types */
        defineEmpty(new TypeToken<Boolean>() {}, () -> Boolean.FALSE);
        defineEmpty(new TypeToken<Byte>() {}, () -> (byte) 0);
        defineEmpty(new TypeToken<Short>() {}, () -> (short) 0);
        defineEmpty(new TypeToken<Integer>() {}, () -> 0);
        defineEmpty(new TypeToken<Long>() {}, () -> 0L);
        defineEmpty(new TypeToken<Float>() {}, () -> 0.0f);
        defineEmpty(new TypeToken<Double>() {}, () -> 0.0);
        defineEmpty(new TypeToken<Character>() {}, () -> '\0');

        /** String and StringBuilder types */
        defineEmpty(new TypeToken<String>() {}, () -> "");
        defineEmpty(new TypeToken<StringBuilder>() {}, StringBuilder::new);
        defineEmpty(new TypeToken<StringBuffer>() {}, StringBuffer::new);

        /** Optional types */
        defineEmpty(new TypeToken<Optional<?>>() {}, Optional::empty);
        defineEmpty(new TypeToken<OptionalInt>() {}, OptionalInt::empty);
        defineEmpty(new TypeToken<OptionalLong>() {}, OptionalLong::empty);
        defineEmpty(new TypeToken<OptionalDouble>() {}, OptionalDouble::empty);

        /** Date and Time types */
        defineEmpty(new TypeToken<LocalDate>() {}, () -> LocalDate.MIN);
        defineEmpty(new TypeToken<LocalDateTime>() {}, () -> LocalDateTime.MIN);
        defineEmpty(new TypeToken<LocalTime>() {}, () -> LocalTime.MIN);
        defineEmpty(new TypeToken<Instant>() {}, () -> Instant.EPOCH);
        defineEmpty(new TypeToken<Duration>() {}, () -> Duration.ZERO);
        defineEmpty(new TypeToken<Period>() {}, () -> Period.ZERO);
        defineEmpty(new TypeToken<ZonedDateTime>() {}, () -> ZonedDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC));
        defineEmpty(new TypeToken<OffsetDateTime>() {}, () -> OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC));
        defineEmpty(new TypeToken<OffsetTime>() {}, () -> OffsetTime.of(LocalTime.MIN, ZoneOffset.UTC));
        defineEmpty(new TypeToken<Year>() {}, () -> Year.of(1));
        defineEmpty(new TypeToken<YearMonth>() {}, () -> YearMonth.of(1, 1));
        defineEmpty(new TypeToken<MonthDay>() {}, () -> MonthDay.of(1, 1));
        defineEmpty(new TypeToken<ZoneId>() {}, () -> ZoneOffset.UTC);
        defineEmpty(new TypeToken<ZoneOffset>() {}, () -> ZoneOffset.UTC);

        /** Numeric types */
        defineEmpty(new TypeToken<BigDecimal>() {}, () -> BigDecimal.ZERO);
        defineEmpty(new TypeToken<BigInteger>() {}, () -> BigInteger.ZERO);
        defineEmpty(new TypeToken<Number>() {}, () -> 0);

        /** Atomic types */
        defineEmpty(new TypeToken<AtomicInteger>() {}, () -> new AtomicInteger(0));
        defineEmpty(new TypeToken<AtomicLong>() {}, () -> new AtomicLong(0L));
        defineEmpty(new TypeToken<AtomicBoolean>() {}, () -> new AtomicBoolean(false));
        defineEmpty(new TypeToken<AtomicReference<?>>() {}, () -> new AtomicReference<>());

        /** UUID and Path */
        defineEmpty(new TypeToken<UUID>() {}, () -> new UUID(0L, 0L));
        defineEmpty(new TypeToken<Path>() {}, () -> Path.of(""));
        defineEmpty(new TypeToken<File>() {}, () -> new File(""));

        /** Collection types */
        defineEmpty(new TypeToken<List<?>>() {}, Collections::emptyList);
        defineEmpty(new TypeToken<Set<?>>() {}, Collections::emptySet);
        defineEmpty(new TypeToken<Map<?, ?>>() {}, Collections::emptyMap);
        defineEmpty(new TypeToken<Collection<?>>() {}, Collections::emptyList);
        defineEmpty(new TypeToken<Queue<?>>() {}, () -> new ArrayDeque<>());
        defineEmpty(new TypeToken<Deque<?>>() {}, () -> new ArrayDeque<>());
        defineEmpty(new TypeToken<Stack<?>>() {}, Stack::new);
        defineEmpty(new TypeToken<Vector<?>>() {}, Vector::new);
        defineEmpty(new TypeToken<ArrayList<?>>() {}, ArrayList::new);
        defineEmpty(new TypeToken<LinkedList<?>>() {}, LinkedList::new);
        defineEmpty(new TypeToken<HashSet<?>>() {}, HashSet::new);
        defineEmpty(new TypeToken<LinkedHashSet<?>>() {}, LinkedHashSet::new);
        defineEmpty(new TypeToken<TreeSet<?>>() {}, TreeSet::new);
        defineEmpty(new TypeToken<HashMap<?, ?>>() {}, HashMap::new);
        defineEmpty(new TypeToken<LinkedHashMap<?, ?>>() {}, LinkedHashMap::new);
        defineEmpty(new TypeToken<TreeMap<?, ?>>() {}, TreeMap::new);
        defineEmpty(new TypeToken<ConcurrentHashMap<?, ?>>() {}, ConcurrentHashMap::new);
        defineEmpty(new TypeToken<Properties>() {}, Properties::new);

        /** Stream types */
        defineEmpty(new TypeToken<Stream<?>>() {}, Stream::empty);
        defineEmpty(new TypeToken<IntStream>() {}, IntStream::empty);
        defineEmpty(new TypeToken<LongStream>() {}, LongStream::empty);
        defineEmpty(new TypeToken<DoubleStream>() {}, DoubleStream::empty);

        /** Regex and Pattern */
        defineEmpty(new TypeToken<Pattern>() {}, () -> Pattern.compile(""));
        defineEmpty(new TypeToken<Matcher>() {}, () -> Pattern.compile("").matcher(""));

        /** Locale and Currency */
        defineEmpty(new TypeToken<Locale>() {}, Locale::getDefault);
        defineEmpty(new TypeToken<Currency>() {}, () -> Currency.getInstance("USD"));

        /** Thread and Executor types */
        defineEmpty(new TypeToken<Thread>() {}, () -> new Thread(() -> {}));
        defineEmpty(new TypeToken<ThreadLocal<?>>() {}, ThreadLocal::new);
        defineEmpty(new TypeToken<ExecutorService>() {}, () -> Executors.newSingleThreadExecutor());
        defineEmpty(new TypeToken<CompletableFuture<?>>() {}, CompletableFuture::new);
        defineEmpty(new TypeToken<Future<?>>() {}, () -> CompletableFuture.completedFuture(null));

        /** Random and Security */
        defineEmpty(new TypeToken<Random>() {}, Random::new);
        defineEmpty(new TypeToken<SecureRandom>() {}, SecureRandom::new);

        /** IO types */
        defineEmpty(new TypeToken<ByteArrayInputStream>() {}, () -> new ByteArrayInputStream(new byte[0]));
        defineEmpty(new TypeToken<ByteArrayOutputStream>() {}, ByteArrayOutputStream::new);
        defineEmpty(new TypeToken<StringReader>() {}, () -> new StringReader(""));
        defineEmpty(new TypeToken<StringWriter>() {}, StringWriter::new);
        defineEmpty(new TypeToken<PrintWriter>() {}, () -> new PrintWriter(new StringWriter()));
        defineEmpty(new TypeToken<Scanner>() {}, () -> new Scanner(""));

        /** Reflection types */
        defineEmpty(new TypeToken<Class<?>>() {}, () -> Object.class);

        /** Exception types */
        defineEmpty(new TypeToken<Exception>() {}, Exception::new);
        defineEmpty(new TypeToken<RuntimeException>() {}, RuntimeException::new);
        defineEmpty(new TypeToken<IllegalArgumentException>() {}, IllegalArgumentException::new);
        defineEmpty(new TypeToken<NullPointerException>() {}, NullPointerException::new);

        /** Buffer types */
        defineEmpty(new TypeToken<Buffer>() {}, () -> ByteBuffer.allocate(0));
        defineEmpty(new TypeToken<ByteBuffer>() {}, () -> ByteBuffer.allocate(0));
        defineEmpty(new TypeToken<CharBuffer>() {}, () -> CharBuffer.allocate(0));
        defineEmpty(new TypeToken<IntBuffer>() {}, () -> IntBuffer.allocate(0));
        defineEmpty(new TypeToken<LongBuffer>() {}, () -> LongBuffer.allocate(0));
        defineEmpty(new TypeToken<FloatBuffer>() {}, () -> FloatBuffer.allocate(0));
        defineEmpty(new TypeToken<DoubleBuffer>() {}, () -> DoubleBuffer.allocate(0));

        /** Charset and Encoding */
        defineEmpty(new TypeToken<Charset>() {}, () -> StandardCharsets.UTF_8);

        /** Formatting */
        defineEmpty(new TypeToken<Formatter>() {}, Formatter::new);
        defineEmpty(new TypeToken<DecimalFormat>() {}, DecimalFormat::new);
        defineEmpty(new TypeToken<SimpleDateFormat>() {}, SimpleDateFormat::new);
    }

    /**
     * Gets an empty/default instance for the specified TypeToken.
     * The instance is cached for future use.
     * 
     * @param <T>       The type of the empty instance to return
     * @param typeToken The TypeToken representing the desired type
     * @return An empty/default instance of the specified type
     * @throws IllegalArgumentException if the type is not supported 
     * @throws ClassCastException if the cast is not supported 
     */
    public static <T> T getEmpty(TypeToken<T> typeToken) {
        Supplier<?> handler = typeHandlers.get(typeToken);

        if (handler != null) {
            Object result = handler.get();

            @SuppressWarnings("unchecked")
            Class<T> rawType = (Class<T>) typeToken.getRawType();
            if (result != null && !rawType.isInstance(result)) {
                throw new ClassCastException(
                        PrettyStrings.error(HANDLER_FOR_TYPE_S_RETURNED_INCOMPATIBLE_TYPE_S,
                                typeToken, result.getClass()));
            }

            return rawType.cast(result);
        } else {
            throw new IllegalArgumentException(
                    PrettyStrings.error(UNSUPPORTED_TYPE_S_LOAD_A_DEFAULT_WITH_THE_METHOD_DEFINE_EMPTY,
                            typeToken.toString()));
        }
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
    public static <T> void defineEmpty(TypeToken<T> typeElement, Supplier<T> defaultSupplier) {
        typeHandlers.putIfAbsent(typeElement, defaultSupplier);
    }

    /**
     * Registers a custom handler for providing empty/default instances of a type
     * using a constant value.
     * 
     * If a handler already exists for the type, it will not be replaced.
     * 
     * @param <T>          The type to register a handler for
     * @param typeElement  The TypeToken representing the type
     * @param defaultValue The default value to return
     */
    public static <T> void defineEmptyValue(TypeToken<T> typeElement, T defaultValue) {
        typeHandlers.putIfAbsent(typeElement, () -> defaultValue);
    }

}