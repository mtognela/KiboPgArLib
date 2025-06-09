package com.kibo.pgar.lib.AnsiClasses;

/**
 * <code>Enum</code> that collects extensive ANSI weights and formatting codes in order to prettify
 * strings in the terminal. It includes basic formatting, colors, advanced effects, and nerd font styles.
 *
 * @author Alessandro Muscio (Kibo)
 * @version 2.0 - Ultimate ANSI Collection
 */
public enum AnsiWeights {
    // ============ BASIC FORMATTING ============
    /** Resets all formatting of the terminal. */
    RESET("\u001B[0m"),
    /** Formats the words in a <i>bold</i> weight. */
    BOLD("\u001B[1m"),
    /** Formats the words in a <i>dim/faint</i> weight. */
    DIM("\u001B[2m"),
    /** Formats the words in a <i>light</i> weight (alias for DIM). */
    LIGHT("\u001B[2m"),
    /** Formats the words in <i>italic</i>. */
    ITALIC("\u001B[3m"),
    /** Formats the words with <i>underline</i>. */
    UNDERLINE("\u001B[4m"),
    /** Formats the words with <i>slow blink</i>. */
    SLOW_BLINK("\u001B[5m"),
    /** Formats the words with <i>rapid blink</i>. */
    RAPID_BLINK("\u001B[6m"),
    /** Formats the words with <i>reverse/invert</i> colors. */
    REVERSE("\u001B[7m"),
    /** Formats the words as <i>concealed/hidden</i>. */
    CONCEALED("\u001B[8m"),
    /** Formats the words with <i>strikethrough</i>. */
    STRIKETHROUGH("\u001B[9m"),

    // ============ ALTERNATIVE FONTS ============
    /** Primary/default font. */
    PRIMARY_FONT("\u001B[10m"),
    /** Alternative font 1 - Sans Serif. */
    ALT_FONT_1("\u001B[11m"),
    /** Alternative font 2 - Serif. */
    ALT_FONT_2("\u001B[12m"),
    /** Alternative font 3 - Monospace. */
    ALT_FONT_3("\u001B[13m"),
    /** Alternative font 4 - Script. */
    ALT_FONT_4("\u001B[14m"),
    /** Alternative font 5 - Decorative. */
    ALT_FONT_5("\u001B[15m"),
    /** Alternative font 6 - Technical. */
    ALT_FONT_6("\u001B[16m"),
    /** Alternative font 7 - Gothic. */
    ALT_FONT_7("\u001B[17m"),
    /** Alternative font 8 - Pixel. */
    ALT_FONT_8("\u001B[18m"),
    /** Alternative font 9 - Retro. */
    ALT_FONT_9("\u001B[19m"),

    // ============ SPECIAL FONTS & STYLES ============
    /** Gothic/Fraktur font style. */
    FRAKTUR("\u001B[20m"),
    /** Double underline. */
    DOUBLE_UNDERLINE("\u001B[21m"),
    /** Normal intensity (not bold, not dim). */
    NORMAL_INTENSITY("\u001B[22m"),
    /** Not italic, not fraktur. */
    NOT_ITALIC("\u001B[23m"),
    /** Not underlined. */
    NOT_UNDERLINED("\u001B[24m"),
    /** Not blinking. */
    NOT_BLINKING("\u001B[25m"),
    /** Proportional spacing. */
    PROPORTIONAL_SPACING("\u001B[26m"),
    /** Not reversed. */
    NOT_REVERSED("\u001B[27m"),
    /** Not concealed/revealed. */
    REVEALED("\u001B[28m"),
    /** Not strikethrough. */
    NOT_STRIKETHROUGH("\u001B[29m"),

    // ============ FOREGROUND COLORS (Standard) ============
    /** Black foreground color. */
    FG_BLACK("\u001B[30m"),
    /** Red foreground color. */
    FG_RED("\u001B[31m"),
    /** Green foreground color. */
    FG_GREEN("\u001B[32m"),
    /** Yellow foreground color. */
    FG_YELLOW("\u001B[33m"),
    /** Blue foreground color. */
    FG_BLUE("\u001B[34m"),
    /** Magenta foreground color. */
    FG_MAGENTA("\u001B[35m"),
    /** Cyan foreground color. */
    FG_CYAN("\u001B[36m"),
    /** White foreground color. */
    FG_WHITE("\u001B[37m"),
    /** Default foreground color. */
    FG_DEFAULT("\u001B[39m"),

    // ============ BACKGROUND COLORS (Standard) ============
    /** Black background color. */
    BG_BLACK("\u001B[40m"),
    /** Red background color. */
    BG_RED("\u001B[41m"),
    /** Green background color. */
    BG_GREEN("\u001B[42m"),
    /** Yellow background color. */
    BG_YELLOW("\u001B[43m"),
    /** Blue background color. */
    BG_BLUE("\u001B[44m"),
    /** Magenta background color. */
    BG_MAGENTA("\u001B[45m"),
    /** Cyan background color. */
    BG_CYAN("\u001B[46m"),
    /** White background color. */
    BG_WHITE("\u001B[47m"),
    /** Default background color. */
    BG_DEFAULT("\u001B[49m"),

    // ============ BRIGHT FOREGROUND COLORS ============
    /** Bright black (gray) foreground color. */
    FG_BRIGHT_BLACK("\u001B[90m"),
    /** Bright red foreground color. */
    FG_BRIGHT_RED("\u001B[91m"),
    /** Bright green foreground color. */
    FG_BRIGHT_GREEN("\u001B[92m"),
    /** Bright yellow foreground color. */
    FG_BRIGHT_YELLOW("\u001B[93m"),
    /** Bright blue foreground color. */
    FG_BRIGHT_BLUE("\u001B[94m"),
    /** Bright magenta foreground color. */
    FG_BRIGHT_MAGENTA("\u001B[95m"),
    /** Bright cyan foreground color. */
    FG_BRIGHT_CYAN("\u001B[96m"),
    /** Bright white foreground color. */
    FG_BRIGHT_WHITE("\u001B[97m"),

    // ============ BRIGHT BACKGROUND COLORS ============
    /** Bright black (gray) background color. */
    BG_BRIGHT_BLACK("\u001B[100m"),
    /** Bright red background color. */
    BG_BRIGHT_RED("\u001B[101m"),
    /** Bright green background color. */
    BG_BRIGHT_GREEN("\u001B[102m"),
    /** Bright yellow background color. */
    BG_BRIGHT_YELLOW("\u001B[103m"),
    /** Bright blue background color. */
    BG_BRIGHT_BLUE("\u001B[104m"),
    /** Bright magenta background color. */
    BG_BRIGHT_MAGENTA("\u001B[105m"),
    /** Bright cyan background color. */
    BG_BRIGHT_CYAN("\u001B[106m"),
    /** Bright white background color. */
    BG_BRIGHT_WHITE("\u001B[107m"),

    // ============ ADVANCED DECORATIONS ============
    /** Disable proportional spacing. */
    DISABLE_PROPORTIONAL_SPACING("\u001B[50m"),
    /** Framed text. */
    FRAMED("\u001B[51m"),
    /** Encircled text. */
    ENCIRCLED("\u001B[52m"),
    /** Overlined text. */
    OVERLINED("\u001B[53m"),
    /** Not framed or encircled. */
    NOT_FRAMED_ENCIRCLED("\u001B[54m"),
    /** Not overlined. */
    NOT_OVERLINED("\u001B[55m"),

    // ============ IDEOGRAM FORMATTING ============
    /** Ideogram underline. */
    IDEOGRAM_UNDERLINE("\u001B[60m"),
    /** Ideogram double underline. */
    IDEOGRAM_DOUBLE_UNDERLINE("\u001B[61m"),
    /** Ideogram overline. */
    IDEOGRAM_OVERLINE("\u001B[62m"),
    /** Ideogram double overline. */
    IDEOGRAM_DOUBLE_OVERLINE("\u001B[63m"),
    /** Ideogram stress marking. */
    IDEOGRAM_STRESS_MARKING("\u001B[64m"),
    /** No ideogram attributes. */
    NO_IDEOGRAM_ATTRIBUTES("\u001B[65m"),

    // ============ SUPERSCRIPT & SUBSCRIPT ============
    /** Superscript text. */
    SUPERSCRIPT("\u001B[73m"),
    /** Subscript text. */
    SUBSCRIPT("\u001B[74m"),

    // ============ NERD FONT STYLE COMBINATIONS ============
    /** Nerd font style: Bold + Bright Green. */
    NERD_SUCCESS("\u001B[1;92m"),
    /** Nerd font style: Bold + Bright Red. */
    NERD_ERROR("\u001B[1;91m"),
    /** Nerd font style: Bold + Bright Yellow. */
    NERD_WARNING("\u001B[1;93m"),
    /** Nerd font style: Bold + Bright Blue. */
    NERD_INFO("\u001B[1;94m"),
    /** Nerd font style: Bold + Bright Magenta. */
    NERD_DEBUG("\u001B[1;95m"),
    /** Nerd font style: Dim + Italic. */
    NERD_COMMENT("\u001B[2;3m"),
    /** Nerd font style: Underline + Cyan. */
    NERD_LINK("\u001B[4;36m"),
    /** Nerd font style: Bold + Underline. */
    NERD_HEADER("\u001B[1;4m"),
    /** Nerd font style: Reverse + Bold. */
    NERD_HIGHLIGHT("\u001B[7;1m"),
    /** Nerd font style: Strikethrough + Red. */
    NERD_DEPRECATED("\u001B[9;31m"),

    // ============ TERMINAL SPECIFIC EFFECTS ============
    /** Save cursor position. */
    SAVE_CURSOR("\u001B[s"),
    /** Restore cursor position. */
    RESTORE_CURSOR("\u001B[u"),
    /** Clear screen. */
    CLEAR_SCREEN("\u001B[2J"),
    /** Clear line. */
    CLEAR_LINE("\u001B[2K"),
    /** Move cursor to home position. */
    CURSOR_HOME("\u001B[H"),
    /** Hide cursor. */
    CURSOR_HIDE("\u001B[?25l"),
    /** Show cursor. */
    CURSOR_SHOW("\u001B[?25h"),

    // ============ HYPERLINK SUPPORT ============
    /** Start hyperlink (requires URL parameter). */
    HYPERLINK_START("\u001B]8;;"),
    /** End hyperlink. */
    HYPERLINK_END("\u001B]8;;\u001B\\"),

    // ============ 24-BIT COLOR HELPERS ============
    /** RGB foreground color prefix (use with RGB values). */
    FG_RGB_PREFIX("\u001B[38;2;"),
    /** RGB background color prefix (use with RGB values). */
    BG_RGB_PREFIX("\u001B[48;2;"),
    /** 256 color foreground prefix (use with color index). */
    FG_256_PREFIX("\u001B[38;5;"),
    /** 256 color background prefix (use with color index). */
    BG_256_PREFIX("\u001B[48;5;"),

    // ============ CUSTOM NERD COMBINATIONS ============
    /** Rainbow effect starter (cycles through colors). */
    RAINBOW_START("\u001B[38;5;196m"),
    /** Matrix style green. */
    MATRIX_GREEN("\u001B[1;32m"),
    /** Retro amber terminal. */
    RETRO_AMBER("\u001B[1;33m"),
    /** Cyberpunk neon. */
    CYBERPUNK_NEON("\u001B[1;5;95m"),
    /** Hacker green glow. */
    HACKER_GLOW("\u001B[1;92m"),
    /** Terminal title style. */
    TERMINAL_TITLE("\u001B[1;4;97m"),
    /** Code syntax highlighting. */
    CODE_SYNTAX("\u001B[38;5;208m"),
    /** File extension highlighting. */
    FILE_EXT("\u001B[38;5;118m"),
    /** Directory highlighting. */
    DIRECTORY("\u001B[1;34m"),
    /** Executable file highlighting. */
    EXECUTABLE("\u001B[1;32m"),
    /** Symbolic link highlighting. */
    SYMLINK("\u001B[1;36m"),
    /** Broken link highlighting. */
    BROKEN_LINK("\u001B[1;5;31m"),

    // ============ GAMING/FUN STYLES ============
    /** ASCII art style. */
    ASCII_ART("\u001B[1;37m"),
    /** Glitch effect. */
    GLITCH("\u001B[5;91m"),
    /** Neon sign effect. */
    NEON_SIGN("\u001B[1;5;93m"),
    /** Hologram effect. */
    HOLOGRAM("\u001B[2;96m"),
    /** Fire effect. */
    FIRE("\u001B[1;91m"),
    /** Ice effect. */
    ICE("\u001B[1;96m"),
    /** Electric effect. */
    ELECTRIC("\u001B[1;5;93m"),
    /** Shadow effect. */
    SHADOW("\u001B[2;90m");

    private final String ansiCode;

    private AnsiWeights(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    @Override
    public String toString() {
        return ansiCode;
    }
    
    /**
     * Gets the raw ANSI escape code.
     * @return the ANSI escape sequence as a string
     */
    public String getCode() {
        return ansiCode;
    }
    
}