package com.kibo.pgar.lib;

import java.io.File;

/**
 * Extension of {@link File} that adds cross-platform utilities.
 */
public class OmniFile extends File {

    /**
     * Constructs a {@code UmniFile} from a sequence of path components.
     * 
     * @param args Path segments to join with the system's file separator.
     */
    public OmniFile(String... args) {
        super(String.join(File.separator, args));
    }

}
