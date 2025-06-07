package com.kibo.pgar.lib.Files;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.kibo.pgar.lib.Formats.AnsiColors;
import com.kibo.pgar.lib.Formats.AnsiWeights;
import com.kibo.pgar.lib.Strings.PrettyStrings;
import com.kibo.pgar.lib.Types.TypeSafeEmpty;
import com.kibo.pgar.lib.Types.TypeT;

public final class JsonService {

    private static final String ERROR_IN_INITIALIZING_THE_READER = PrettyStrings.prettify(AnsiColors.RED,
            AnsiWeights.BOLD, null, "Error in initializing the reader");
    private static final String ERROR_IN_INITIALIZING_THE_WRITER = PrettyStrings.prettify(AnsiColors.RED,
            AnsiWeights.BOLD, null, "Error in initializing the writer");
    private static final Gson gson = new Gson();

    public static <T> T read(File file, Class<T> type) {

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            System.out.println(ERROR_IN_INITIALIZING_THE_READER);
            System.out.println(e.getMessage());
            return TypeSafeEmpty.getEmpty(new TypeT<T>());
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
