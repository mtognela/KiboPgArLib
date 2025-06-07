package com.kibo.pgar.lib;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public final class JsonService {

    private static final String ERROR_IN_INITIALIZING_THE_READER = "Error in initializing the reader";
    private static final String ERROR_IN_INITIALIZING_THE_WRITER = "Error in initializing the writer:";

    private static final Gson gson = new Gson();

    @SuppressWarnings("unchecked")
    public static <T> T read(File file, Class<T> type) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            System.out.println(ERROR_IN_INITIALIZING_THE_READER);
            System.out.println(e.getMessage());
            return emptyInstanceForType(type);
        }
    }

    public static <T> boolean write(File file, T input) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(input, writer);
            return true;
        } catch (JsonIOException | IOException e) {
            System.out.println(ERROR_IN_INITIALIZING_THE_WRITER);
            System.out.println(e.getMessage());
            return false;
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> T emptyInstanceForType(Class<T> type) {
        if (List.class.isAssignableFrom(type))
            return (T) Collections.emptyList();
        else if (Set.class.isAssignableFrom(type))
            return (T) Collections.emptySet();
        else if (SortedSet.class.isAssignableFrom(type))
            return (T) Collections.unmodifiableSortedSet(new TreeSet<>());
        else if (NavigableSet.class.isAssignableFrom(type))
            return (T) Collections.unmodifiableNavigableSet(new TreeSet<>());
        else if (Queue.class.isAssignableFrom(type))
            return (T) new LinkedList<>();
        else if (Deque.class.isAssignableFrom(type))
            return (T) new LinkedList<>();
        else if (Map.class.isAssignableFrom(type))
            return (T) Collections.emptyMap();
        else if (SortedMap.class.isAssignableFrom(type))
            return (T) Collections.unmodifiableSortedMap(new TreeMap<>());
        else if (NavigableMap.class.isAssignableFrom(type))
            return (T) Collections.unmodifiableNavigableMap(new TreeMap<>());

        return null;
    }
}
