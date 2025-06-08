package com.kibo.pgar.lib.Files;

import java.io.File;

/**
 * <code>Class</code> for simple universal file  
 * 
 * @author Mattia Tognela (mtognela)
 * @version 1.4
 */
public final class OmniFile {

    private OmniFile() {
    }

    /**
     * Constructs a {@code File} from a sequence of path components.
     * 
     * @param args Path segments to join with the system's file separator.
     * @return the constructed File  
     */
    public static File of(String... args) {
        return new File(String.join(File.separator, args));
    }


    /**
     * Constructs a {@code File} from a sequence of path components.
     * 
     * @param path     Path segments to join with the system's file separator.
     * @param fileName the file name
     * @return the constructed File
     */
    public static File of(String[] path, String fileName) {
        String[] fullPath = new String[path.length + 1];
        System.arraycopy(path, 0, fullPath, 0, path.length);

        fullPath[path.length] = fileName;
        return new File(String.join(File.separator, fullPath));
    }

}
