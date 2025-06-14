package com.kibo.pgar.lib.Files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.kibo.pgar.lib.Strings.PrettyStrings;

/**
 * <code>Class</code> that let's you serialize and deserialize objects to file.
 * 
 * @author Alessandro Muscio (Kibo) and Mattia Tognela (mtognela)
 */
public final class FileService {
    
    private static final String NOT_FOUND_ERROR = PrettyStrings.error(
            "Attention! Can't find the file %s\n");

    private static final String WRITING_ERROR = PrettyStrings.error(
            "Attention! Problem writing the file %s\n");

    private static final String READING_ERROR = PrettyStrings.error(
            "Attention! Problem reading the file %s\n");

    private static final String CLOSING_ERROR = PrettyStrings.error(
            "Attention! Problem closing the file %s\n");

    private FileService()  {
    }

    /**
     * Serialize whatever object is given, as long as implements
     * <code>Serializable</code>.
     * 
     * @param <T>  The <i>generic</i> used for the object to serialize that needs to
     *             implement <code>Serializable</code>.
     * @param file The file where to save the serialized object.
     * @param obj  The object to serialize and save.
     */
    public static <T extends Serializable> void serializeObject(File file, Object obj) {
        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

            output.writeObject(obj);
        } catch (FileNotFoundException e) {
            System.out.printf(FileService.NOT_FOUND_ERROR, file.getAbsolutePath());
        } catch (IOException e) {
            System.out.printf(FileService.WRITING_ERROR, file.getName());
        }

        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                System.out.printf(FileService.CLOSING_ERROR, file.getName());
            }
        }
    }

    /**
     * Deserialize whatever object is saved in the given file. The deserialized file
     * will be cast in the given class.
     * 
     * @param <T>    The generics used for the class of the object to deserialize
     *               that needs to implement <code>Serializable</code>.
     * @param file   The file where to find the serialized object.
     * @param objCls The class to cast the serialized object in.
     * 
     * @return A <code><span>&#60;</span>T<span>&#62;</span> </code> representing
     *         the deserialized object.
     */
    public static <T extends Serializable> T deserializeObject(File file, Class<T> objCls) {
        Object read = null;
        ObjectInputStream input = null;

        try {
            input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));

            read = input.readObject();
        } catch (FileNotFoundException e) {
            System.out.printf(FileService.NOT_FOUND_ERROR, file.getAbsolutePath());
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf(FileService.READING_ERROR, file.getName());
        }

        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                System.out.printf(FileService.CLOSING_ERROR, file.getName());
            }
        }

        return objCls.cast(read);
    }
}
