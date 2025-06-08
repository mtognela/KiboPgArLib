package com.kibo.pgar.lib.AnsiClasses;

/**
 * <code>Enum</code> that collects some ANSI colors in order to prettify strings in the terminal. It
 * can color both the words and the background of strings.
 * 
 * @author Alessandro Muscio (Kibo) and Mattia Tognela (mtognela)
 * @version 1.5
 */
public enum AnsiColors {
    /** Resets all attributes. */
    RESET("\u001B[0m"),

    /** Inverts background and foreground. */
    INVERT("\u001B[7m"),

    /** Colors the words <i>black</i>. */
    BLACK("\u001B[30m"),

    /** Colors the words <i>red</i>. */
    RED("\u001B[31m"),

    /** Colors the words <i>green</i>. */
    GREEN("\u001B[32m"),

    /** Colors the words <i>yellow</i>. */
    YELLOW("\u001B[33m"),

    /** Colors the words <i>blue</i>. */
    BLUE("\u001B[34m"),

    /** Colors the words <i>purple</i>. */
    PURPLE("\u001B[35m"),

    /** Colors the words <i>cyan</i>. */
    CYAN("\u001B[36m"),

    /** Colors the words <i>white</i>. */
    WHITE("\u001B[37m"),

    /** Colors the background <i>black</i>. */
    BLACK_BACKGROUND("\u001B[40m"),

    /** Colors the background <i>red</i>. */
    RED_BACKGROUND("\u001B[41m"),

    /** Colors the background <i>green</i>. */
    GREEN_BACKGROUND("\u001B[42m"),

    /** Colors the background <i>yellow</i>. */
    YELLOW_BACKGROUND("\u001B[43m"),

    /** Colors the background <i>blue</i>. */
    BLUE_BACKGROUND("\u001B[44m"),

    /** Colors the background <i>purple</i>. */
    PURPLE_BACKGROUND("\u001B[45m"),

    /** Colors the background <i>cyan</i>. */
    CYAN_BACKGROUND("\u001B[46m"),

    /** Colors the background <i>white</i>. */
    WHITE_BACKGROUND("\u001B[47m"),

    /** Colors the words <i>bright black</i>. */
    BRIGHT_BLACK("\u001B[90m"),

    /** Colors the words <i>bright red</i>. */
    BRIGHT_RED("\u001B[91m"),

    /** Colors the words <i>bright green</i>. */
    BRIGHT_GREEN("\u001B[92m"),

    /** Colors the words <i>bright yellow</i>. */
    BRIGHT_YELLOW("\u001B[93m"),

    /** Colors the words <i>bright blue</i>. */
    BRIGHT_BLUE("\u001B[94m"),

    /** Colors the words <i>bright purple</i>. */
    BRIGHT_PURPLE("\u001B[95m"),

    /** Colors the words <i>bright cyan</i>. */
    BRIGHT_CYAN("\u001B[96m"),

    /** Colors the words <i>bright white</i>. */
    BRIGHT_WHITE("\u001B[97m"),

    // --- Bright Background Colors ---
    /** Colors the background <i>bright black</i>. */
    BRIGHT_BLACK_BACKGROUND("\u001B[100m"),

    /** Colors the background <i>bright red</i>. */
    BRIGHT_RED_BACKGROUND("\u001B[101m"),

    /** Colors the background <i>bright green</i>. */
    BRIGHT_GREEN_BACKGROUND("\u001B[102m"),

    /** Colors the background <i>bright yellow</i>. */
    BRIGHT_YELLOW_BACKGROUND("\u001B[103m"),

    /** Colors the background <i>bright blue</i>. */
    BRIGHT_BLUE_BACKGROUND("\u001B[104m"),

    /** Colors the background <i>bright purple</i>. */
    BRIGHT_PURPLE_BACKGROUND("\u001B[105m"),

    /** Colors the background <i>bright cyan</i>. */
    BRIGHT_CYAN_BACKGROUND("\u001B[106m"),

    /** Colors the background <i>bright white</i>. */
    BRIGHT_WHITE_BACKGROUND("\u001B[107m");

    private final String ansiCode;

    private AnsiColors(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    @Override
    public String toString() {
        return ansiCode;
    }
}
