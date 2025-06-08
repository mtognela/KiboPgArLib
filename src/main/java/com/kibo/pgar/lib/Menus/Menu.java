package com.kibo.pgar.lib.Menus;

import java.io.Serializable;

import com.kibo.pgar.lib.Strings.PrettyStrings;
import com.kibo.pgar.lib.AnsiClasses.Alignment;
import com.kibo.pgar.lib.AnsiClasses.AnsiColors;
import com.kibo.pgar.lib.AnsiClasses.AnsiWeights;
import com.kibo.pgar.lib.Calcolus.KnownProblems;
import com.kibo.pgar.lib.Inputs.InputData;

/**
 * The <strong>Menu</strong> class creates a menu with multiple entry supposing
 * that zero is always the exit option. The class also contains some method that
 * may result useful in visualizing the menu.
 *
 * @author Alessandro Muscio and Mattia Tognela (mtognela)
 * @version 1.4
 */
public class Menu implements Serializable {
    private static final String EXIT_ENTRY = PrettyStrings.prettify(
        AnsiColors.CYAN, AnsiWeights.BOLD, null,
        "0. Exit");

    private static final String INSERT_REQUEST = PrettyStrings.prettify(
        AnsiColors.GREEN, AnsiWeights.BOLD, null,
        ": ");

    private static final String NEGATIVE_MILLIS_ERROR = PrettyStrings.error(
            "Attention! You can't have negative time");

    /**
     * Represent the title of the menu.
     */
    private final String title;

    /**
     * Contains all the menu entries.
     */
    private final String[] entries;

    /**
     * Represent if you want to use the exit entry or not.
     */
    private final boolean useExitEntry;

    /**
     * Represents the length of the frame.
     */
    private final int frameLength;

    private final FrameSettings frameSettings;

    /**
     * Constructor that creates a <code>Menu</code> object specifying a title, the
     * entries of the menu, if you want the exit entry or not, if you want the title
     * centred and if you also want the vertical frame in the title. It will also
     * automatically calculate the frame length.
     *
     * @param title            Represents the title of the menu.
     * @param entries          Represents the entries of the menu.
     * @param useExitEntry     If you want the exit entry or not.
     * @param alignament       If you want the title to be centred or not.
     * @param useVerticalFrame If you want to use the vertical frame or not.
     */
    public Menu(String title, String[] entries, boolean useExitEntry, Alignment alignment, boolean useVerticalFrame) {
        this.title = title;
        this.entries = entries;
        this.useExitEntry = useExitEntry;
        this.frameLength = calculateFrameLength(title, entries);
        this.frameSettings = new FrameSettings(frameLength, alignment, useVerticalFrame);
    }

    /**
     * Calculates the frame length by measuring the length of the title and of all
     * the entries of the menu accounting for their number and the ". " string
     * before the actual entry.
     * 
     * @param title   The title of the menu.
     * @param entries The entries of the menu.
     * 
     * @return An <code>int</code> representing the length of the frame.
     */
    private int calculateFrameLength(String title, String[] entries) {
        int frameLength = title.length();

        for (int i = 0; i < entries.length; i++)
            frameLength = Math.max(frameLength, entries[i].length() + KnownProblems.countIntegerDigits(i + 1) + 2);

        return frameLength + 10;
    }

    /**
     * Prints the menu: first the framed title and then all the entries.
     */
    private void printMenu() {
        StringBuffer menu = new StringBuffer();

        menu.append(PrettyStrings.frame(title, frameSettings));

        for (int i = 0; i < entries.length; i++)
            menu.append(i != entries.length - 1 ? String.format("%d. %s\n", (i + 1), entries[i])
                    : String.format("%d. %s", (i + 1), entries[i]));

        if (useExitEntry)
            menu.append(PrettyStrings.isolatedLine(EXIT_ENTRY));

        System.out.println(menu);
    }

    /**
     * Prints the menu and lets the user choose an option from it.
     * 
     * @return An <code>int</code> representing the choice of the user.
     *         When useExitEntry is true: 0 = Exit, 1-N = menu entries
     *         When useExitEntry is false: 1-N = menu entries
     */
    public int choose() {
        printMenu();

        if (useExitEntry) {
            
            return InputData.readIntegerBetween(INSERT_REQUEST, 0, entries.length);
        } else {
            return InputData.readIntegerBetween(INSERT_REQUEST, 1, entries.length);
        }
    }

    /**
     * Stops the program for a certain amount of milliseconds.
     * 
     * @param milliseconds The number of milliseconds to stop the program.
     * 
     * @throws InterruptedException If any thread has interrupted the current
     *                              thread. The <i>interrupted status</i> of the
     *                              current thread is cleared when this exception is
     *                              thrown.
     */
    public static void wait(int milliseconds) throws InterruptedException {
        try {
            Thread.sleep(milliseconds);
        } catch (IllegalArgumentException e) {
            System.out.println(NEGATIVE_MILLIS_ERROR);
        }
    }
}