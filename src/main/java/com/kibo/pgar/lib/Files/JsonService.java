package com.kibo.pgar.lib.Files;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.kibo.pgar.lib.Strings.PrettyStrings;
import com.kibo.pgar.lib.Types.TypeSafe;

public final class JsonService {

    private static final String ERROR_IN_INITIALIZING_THE_READER = PrettyStrings.error("Error in initializing the reader");
    private static final String ERROR_IN_INITIALIZING_THE_WRITER = PrettyStrings.error("Error in initializing the writer");
    private static final Gson gson = new Gson();

    public static <T> T read(File file, TypeToken<T> type) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            System.out.println(ERROR_IN_INITIALIZING_THE_READER);
            System.out. println(e.getMessage());
            return (T) TypeSafe.getEmpty(new TypeToken<T>() {});
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

}
