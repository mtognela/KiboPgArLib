package com.kibo.pgar.lib.Strings;

import com.kibo.pgar.lib.Formats.Alignment;
import com.kibo.pgar.lib.Formats.AnsiColors;
import com.kibo.pgar.lib.Formats.AnsiDecorations;
import com.kibo.pgar.lib.Formats.AnsiWeights;
import com.kibo.pgar.lib.Menus.FrameSettings;

/**
 * <code>Class</code> that let's you prettify the strings to output in the
 * terminal.
 * 
 * @author Alessandro Muscio (Kibo) and Mattia Tognela 
 * @version 1.2
 */
public final class PrettyStrings {
    private static final char SPACE = ' ';
    private static final char NEW_LINE = '\n';

    private PrettyStrings() {
    }

    public static String frame(String toFrame, FrameSettings settings) {
        StringBuffer framed = new StringBuffer();
        String horizontalFrame = PrettyStrings.repeatChar(settings.getHorizontalFrame(), settings.getWidth());

        framed.append(horizontalFrame);
        framed.append(PrettyStrings.NEW_LINE);

        if (settings.isVerticalFrameEnabled()) {
            settings.setWidth(settings.getWidth() - 2);

            framed.append(settings.getVerticalFrame());
        }

        if (settings.getAlignment().equals(Alignment.CENTER))
            framed.append(PrettyStrings.center(toFrame, settings.getWidth()));
        else
            framed.append(PrettyStrings.column(
                    toFrame,
                    settings.getWidth(),
                    settings.getAlignment().equals(Alignment.LEFT)));

        framed.append(settings.isVerticalFrameEnabled() ? settings.getVerticalFrame() : "");

        framed.append(PrettyStrings.isolatedLine(horizontalFrame));

        return framed.toString();
    }

    /**
     * Columnize the given <code>String</code>, to the left or to the right.
     * 
     * @param toColumnize The <code>String</code> to columnize.
     * @param width       The width where to columnize the <code>String</code>.
     * @param left        If columnize it to the left or to the right.
     * 
     * @return A <code>String</code> representing the given one columnized.
     */
    public static String column(String toColumnize, int width, boolean left) {
        int toColumnizeLength = toColumnize.length();
        int charsToPrint = Math.min(width, toColumnizeLength);

        String columned = toColumnizeLength > charsToPrint ? toColumnize.substring(0, charsToPrint)
                : toColumnize;
        String spaces = PrettyStrings.repeatChar(PrettyStrings.SPACE, width - toColumnizeLength);

        return left ? columned.concat(spaces) : spaces.concat(columned);
    }

    /**
     * Centers the given <code>String</code> in the given space.
     * 
     * @param toCenter The <code>String</code> to center.
     * @param width    The width where to center the <code>String</code>.
     * 
     * @return A <code>String</code> representing the given one centered.
     */
    public static String center(String toCenter, int width) {
        int toCenterLength = toCenter.length();

        if (toCenterLength > width)
            return toCenter.substring(0, width);

        if (toCenterLength == width)
            return toCenter;

        StringBuffer builder = new StringBuffer(width);
        int whitespaces = width - toCenterLength;
        int whitespacesBefore = Math.floorDiv(whitespaces, 2);
        int whitespacesAfter = whitespaces - whitespacesBefore;

        builder.append(PrettyStrings.repeatChar(PrettyStrings.SPACE, whitespacesBefore));
        builder.append(toCenter);
        builder.append(PrettyStrings.repeatChar(PrettyStrings.SPACE, whitespacesAfter));

        return builder.toString();
    }

    /**
     * Repeats a given <code>char</code> for a given number of times.
     * 
     * @param character The <code>char</code> to repeat.
     * @param times     The number of times to repeat the <code>char</code>.
     * 
     * @return A <code>String</code> representing the given <code>char</code>
     *         repeated the given number of times.
     */
    public static String repeatChar(char character, int times) {
        return String.valueOf(character).repeat(Math.max(0, times));
    }

    /**
     * Isolates the given <code>String</code>.
     * 
     * @param toIsolate The <code>String</code> to isolate.
     * 
     * @return A <code>String</code> isolated in it's own line.
     */
    public static String isolatedLine(String toIsolate) {
        StringBuffer builder = new StringBuffer();
        
        builder.append(PrettyStrings.NEW_LINE);
        builder.append(toIsolate);
        builder.append(PrettyStrings.NEW_LINE);

        return builder.toString();
    }

    /**
     * Prettifies the given <code>String</code> by adding a <i>color</i>,
     * <i>weight</i> and <i>decoration</i>, if given.
     * 
     * @param color      The color given to the <code>String</code>,
     *                   <code>null</code> for default color.
     * @param weight     The weight given to the <code>String</code>,
     *                   <code>null</code> for default weight.
     * @param decoration The decoration given to the <code>String</code>,
     *                   <code>null</code> for no decoration.
     * @param format     The <code>String</code> to be formatted and prettified.
     * @param args       The args that format that format the <code>String</code>
     * 
     * @return A <code>String</code> representing the given one formatted and prettified.
     */
    public static String prettify(AnsiColors color, AnsiWeights weight, AnsiDecorations decoration, String format,
            Object... args) {
        StringBuffer builder = new StringBuffer();
        boolean reset = false;

        if (color != null) {
            reset = true;
            builder.append(color);
        }

        if (weight != null) {
            reset = true;
            builder.append(weight);
        }

        if (decoration != null) {
            reset = true;
            builder.append(decoration);
        }

        builder.append(String.format(format, args));

        if (reset)
            builder.append(AnsiColors.RESET);

        return builder.toString();
    }

    /**
     * For backwords compatabily only 
     * 
     * Prettifies the given <code>String</code> by adding a <i>color</i>,
     * <i>weight</i> and <i>decoration</i>, if given.
     * 
     * @param color      The color given to the <code>String</code>,
     *                   <code>null</code> for default color.
     * @param weight     The weight given to the <code>String</code>,
     *                   <code>null</code> for default weight.
     * @param decoration The decoration given to the <code>String</code>,
     *                   <code>null</code> for no decoration.
     * @param toPrettify The <code>String</code> to be formatted and prettified.
     * 
     * @return A <code>String</code> representing the given one prettified.
     */
    public static String prettify(String toPrettify, AnsiColors color, AnsiWeights weight, AnsiDecorations decoration) {
        StringBuffer builder = new StringBuffer();
        boolean reset = false;

        if (color != null) {
            reset = true;
            builder.append(color);
        }

        if (weight != null) {
            reset = true;
            builder.append(weight);
        }

        if (decoration != null) {
            reset = true;
            builder.append(decoration);
        }

        builder.append(toPrettify);

        if (reset)
            builder.append(AnsiColors.RESET);

        return builder.toString();
    }

}
