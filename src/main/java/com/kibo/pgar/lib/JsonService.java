package com.kibo.pgar.lib;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public final class JsonService {

    private static final String ERROR_IN_INITIALIZING_THE_READER = "Error in initializing the reader";
    private static final String ERROR_IN_INITIALIZING_THE_WRITER = "Error in initializing the writer:";

    private static final Gson gson = new Gson();

    private <T> T readGeneric(File file) {
        try (FileReader reader = new FileReader(file)) {
            TypeToken<T> typeToken = new  TypeToken<>() {};
            return gson.fromJson(reader, typeToken.getType());
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            System.out.println(ERROR_IN_INITIALIZING_THE_READER);
            System.out.println(e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unused")
    private <T extends Serializable> List<T> read(File file) {
        List<T> toReturn = readGeneric(file);
        return toReturn != null ?  toReturn : Collections.emptyList(); 
    }


    public <T extends Serializable> boolean write(File file, T input) {
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
