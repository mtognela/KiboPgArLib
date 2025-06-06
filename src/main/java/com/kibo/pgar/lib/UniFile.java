package com.kibo.pgar.lib;

import java.io.File;

/**
 * Extension of {@link File} that adds cross-platform utilities.
 */
public class UniFile extends File {

    /**
     * Constructs a {@code UniversalFile} from a sequence of path components.
     * 
     * @param args Path segments to join with the system's file separator.
     */
    public UniFile(String... args) {
        super(String.join(File.separator, args));
    }

      /**
     * Casts this {@code UniversalFile} to a regular {@link File}.
     * 
     * @return a reference to this object as a {@link File}.
     */
    public File castToFile() {
        return this;
    }

}
