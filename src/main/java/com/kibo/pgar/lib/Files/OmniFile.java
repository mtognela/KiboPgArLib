package com.kibo.pgar.lib.Files;

import java.io.File;

/**
 * <code>Class</code> for simple universal file  
 * 
 * @author Mattia Tognela (mtognela)
 * @version 1.1
 */
public final class OmniFile extends File {

    /**
     * Constructs a {@code UmniFile} from a sequence of path components.
     * 
     * @param args Path segments to join with the system's file separator.
     */
    public OmniFile(String... args) {
        super(String.join(File.separator, args));
    }

}
