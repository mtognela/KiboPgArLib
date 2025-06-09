package com.kibo.pgar.lib.Files;

import java.io.File;
import java.nio.file.Paths;

import com.kibo.pgar.lib.Strings.PrettyStrings;

/**
 * <code>Class</code> for simple universal file operations using Path
 * 
 * @author Mattia Tognela (mtognela)
 * @version 2.0
 */
public final class OmniFile {
    
    private static final String AT_LEAST_ONE_PATH_COMPONENT_MUST_BE_PROVIDED = "At least one path component must be provided";

    private OmniFile() {}
    
    /**
     * Constructs a {@code Path} from a sequence of path components.
     * Convenience method that handles empty array case.
     * 
     * @param args Path segments to join with the system's file separator
     * @return the constructed File
     * @throws IllegalArgumentException if no arguments are provided
     */
    public static File of(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(PrettyStrings.error(AT_LEAST_ONE_PATH_COMPONENT_MUST_BE_PROVIDED));
        }
        if (args.length == 1) {
            return Paths.get(args[0]).toFile();
        }
        String first = args[0];
        String[] more = new String[args.length - 1];
        System.arraycopy(args, 1, more, 0, args.length - 1);
        return Paths.get(first, more).toFile();
    }

}