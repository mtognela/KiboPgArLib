package com.kibo.pgar.lib.Inputs;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

import com.kibo.pgar.lib.Strings.PrettyStrings;

public final class InputData {
    private static final Scanner reader = createScanner();

    private static final String ALPHANUMERIC_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
    private static final String ALPHANUMERIC_CHARACTERS_ERROR = "\nOnly alphanumeric characters are allowed.";
    private static final String EMPTY_STRING_ERROR = "\nNo characters were inserted.";
    private static final String ALLOWED_CHARACTERS_ERROR = "\nThe only allowed characters are: %s";
    private static final String INTEGER_FORMAT_ERROR = "\nThe inserted data is in an incorrect format. An integer is required.";
    private static final String DOUBLE_FORMAT_ERROR = "\nThe inserted data is in an incorrect format. A double is required.";
    private static final String MINIMUM_ERROR_INTEGER = "\nA value greater or equal than %d is required.";
    private static final String MAXIMUM_ERROR_INTEGER = "\nA value less or equal than %d is required.";
    private static final String MINIMUM_ERROR_DOUBLE = "\nA value greater or equal than %.2f is required.";
    private static final String MAXIMUM_ERROR_DOUBLE = "\nA value less or equal than %.2f is required.";
    private static final String INVALID_ANSWER = "\nThe answer is not valid!";

    private static final String INSERT_REQUEST = ": ";

    private InputData() {
    }

    /**
     * Flushes the input buffer by advancing the scanner to the next line.<br>
     * <br>
     *
     * This method consumes and discards the remainder of the current line of input,
     * typically used to clear the input buffer after reading numeric input with
     * <code>Scanner</code> methods like <code>nextInt()</code> or
     * <code>nextDouble()</code>.<br>
     * <br>
     *
     * This prevents unexpected behavior caused by leftover newline characters
     * when switching between different input types.
     */
    private static void flushReader() {
        reader.nextLine();
    }

    /**
     * Creates a new {@link Scanner} instance configured to read user input from
     * {@link System#in},
     * using newline ({@code "\n"}) as the delimiter pattern.
     * <p>
     * The scanner will tokenize input by lines, making {@link Scanner#next()}
     * return
     * entire lines of input at a time. This matches common console input behavior
     * across different execution environments.
     * </p>
     * 
     * @return A configured Scanner instance reading from standard input
     * @see Scanner#useDelimiter(String)
     * @see System#in
     */
    private static Scanner createScanner() {
        return new Scanner(System.in).useDelimiter("\n");
    }

    /**
     * Verifies if the given message has only alphanumeric characters.
     *
     * @param message The message to verify.
     * @return A <code>boolean</code> representing if the message is alphanumeric or
     *         not.
     */
    private static boolean hasAlphanumericCharacters(String message) {
        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if (ALPHANUMERIC_CHARACTERS.indexOf(currentChar) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @author Mattia Tognela
     * @param message Let you deliver a message for the user
     * @return A <code>String</code> from the method <code>reader.nextLine()</code>
     */
    public static String readString(String message) {
        return readString(message, InputData::defaultPrint);
    }

    /**
     * @author Mattia Tognela
     * @param message Let you deliver a message for the user
     * @param print   Let you execute the print statement that you like the best
     * @return A <code>String</code> from the method <code>reader.nextLine()</code>
     */
    public static String readString(String message, Consumer<? super String> print) {
        print.accept(message);
        return reader.nextLine();
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. If it isn't a <code>String</code> an error message is printed. It's
     * also possible to select if the inserted text needs to be alphanumeric or not.
     *
     * @param message      The message to print.
     * @param alphanumeric If the input needs to be alphanumeric or not.
     * @return A <code>String</code> representing the user input.
     */
    public static String readString(String message, boolean alphanumeric) {
        return readString(message, alphanumeric, InputData::defaultPrint);
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. If it isn't a <code>String</code> an error message is printed. It's
     * also possible to select if the inserted text needs to be alphanumeric or not.
     *
     * @param message      The message to print.
     * @param alphanumeric If the input needs to be alphanumeric or not.
     * @param print        Let you execute the print statement that you like the
     *                     best
     * @return A <code>String</code> representing the user input.
     */
    public static String readString(String message, boolean alphanumeric, Consumer<? super String> print) {
        boolean isAlphanumeric;
        String read;

        if (alphanumeric) {
            do {
                print.accept(message);
                read = reader.next().trim();
                isAlphanumeric = hasAlphanumericCharacters(read);

                if (!isAlphanumeric) {
                    PrettyStrings.printlnError(ALPHANUMERIC_CHARACTERS_ERROR);
                }
            } while (!isAlphanumeric);
        } else {
            print.accept(message + INSERT_REQUEST);
            read = reader.next().trim();
        }

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user, given that it isn't empty. If it isn't a <code>String</code> an
     * error message is printed. It's also possible to select if the inserted text
     * needs to be alphanumeric or not.
     *
     * @param message      The message to print.
     * @param alphanumeric If the input needs to be alphanumeric or not.
     * @return A <code>String</code> representing the user input.
     */
    public static String readStringNotEmpty(String message, boolean alphanumeric) {
        return readStringNotEmpty(message, alphanumeric, InputData::defaultPrint);
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user, given that it isn't empty. If it isn't a <code>String</code> an
     * error message is printed. It's also possible to select if the inserted text
     * needs to be alphanumeric or not.
     *
     * @param message      The message to print.
     * @param alphanumeric If the input needs to be alphanumeric or not.
     * @param print        Let you execute the print statement that you like the
     *                     best
     * @return A <code>String</code> representing the user input.
     */
    public static String readStringNotEmpty(String message, boolean alphanumeric, Consumer<? super String> print) {
        boolean isStringEmpty = true;
        String read;

        do {
            read = readString(message, alphanumeric, print);
            isStringEmpty = read.isBlank();

            if (isStringEmpty) {
                System.out.println(EMPTY_STRING_ERROR);
            }
        } while (isStringEmpty);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will take the first <code>char</code> in it and verify if it is
     * in the <code>allowed</code> characters, if not, an error message will be
     * printed.
     *
     * @param message The message to print.
     * @param allowed All the allowed characters.
     * @return A <code>char</code> representing the character that was read.
     */
    public static char readChar(String message, String allowed) {
        return readChar(message, allowed, m -> defaultPrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will take the first <code>char</code> in it and verify if it is
     * in the <code>allowed</code> characters, if not, an error message will be
     * printed.
     *
     * @param message The message to print.
     * @param allowed All the allowed characters.
     * @param print   Let you execute the print statement that you like the best
     * @return A <code>char</code> representing the character that was read.
     */
    public static char readChar(String message, String allowed, Consumer<? super String> print) {
        boolean isAllowed = false;
        String read;
        char readChar;

        do {
            read = readStringNotEmpty(message, false, print);
            readChar = read.charAt(0);

            if (allowed.indexOf(readChar) != -1) {
                isAllowed = true;
            } else {
                PrettyStrings.printlnError(ALLOWED_CHARACTERS_ERROR,
                        Arrays.toString(allowed.toCharArray()));
            }
        } while (!isAllowed);

        return readChar;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer.
     *
     * @param message The message to print.
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readInteger(String message) {
        return readInteger(message, m -> defaultPrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer.
     *
     * @param message The message to print.
     * @param print   Let you execute the print statement that you like the best
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readInteger(String message, Consumer<? super String> print) {
        boolean isInteger;
        int read = 0;

        do {
            try {
                print.accept(message);
                read = reader.nextInt();
                isInteger = true;
            } catch (InputMismatchException e) {
                System.out.println(INTEGER_FORMAT_ERROR);
                isInteger = false;
            } finally {
                flushReader();
            }
        } while (!isInteger);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer or if the integer inserted is less than <code>min</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readIntegerWithMinimum(String message, int min) {
        return readIntegerWithMinimum(message, min, m -> defaultPrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer or if the integer inserted is less than <code>min</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @param print   Let you execute the print statement that you like the best
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readIntegerWithMinimum(String message, int min, Consumer<? super String> print) {
        boolean isAboveMin = false;
        int read;

        do {
            read = readInteger(message, print);

            if (read >= min) {
                isAboveMin = true;
            } else {
                PrettyStrings.printlnError(MINIMUM_ERROR_INTEGER, min);
            }
        } while (!isAboveMin);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer or if the integer inserted is greater than <code>max</code>.
     *
     * @param message The message to print.
     * @param max     The maximum value to read.
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readIntegerWithMaximum(String message, int max) {
        return readIntegerWithMaximum(message, max, m -> defaultPrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer or if the integer inserted is greater than <code>max</code>.
     *
     * @param message The message to print.
     * @param max     The maximum value to read.
     * @param print   Let you execute the print statement that you like the best
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readIntegerWithMaximum(String message, int max, Consumer<? super String> print) {
        boolean isBelowMax = false;
        int read;

        do {
            read = readInteger(message, print);

            if (read <= max) {
                isBelowMax = true;
            } else {
                PrettyStrings.printlnError(MAXIMUM_ERROR_INTEGER, max);
            }
        } while (!isBelowMax);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer or if the integer inserted isn't between or equal to
     * <code>min</code> and <code>max</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @param max     The maximum value to read.
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readIntegerBetween(String message, int min, int max) {
        return readIntegerBetween(message, min, max, m -> defaultPrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't an
     * integer or if the integer inserted isn't between or equal to
     * <code>min</code> and <code>max</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @param max     The maximum value to read.
     * @param print   Let you execute the print statement that you like the best
     * @return An <code>int</code> representing the integer that was read.
     */
    public static int readIntegerBetween(String message, int min, int max, Consumer<? super String> print) {
        boolean isBetweenMinMax = false;
        int read;

        do {
            read = readInteger(message, print);

            if (read < min) {
                PrettyStrings.printlnError(MINIMUM_ERROR_INTEGER, min);
            } else if (read > max) {
                PrettyStrings.printlnError(MAXIMUM_ERROR_INTEGER, max);
            } else {
                isBetweenMinMax = true;
            }
        } while (!isBetweenMinMax);

        return read;
    }

    /**
     * Reads an integer choice between min and max values.
     *
     * @param min The minimum value to read.
     * @param max The maximum value to read.
     * @return An <code>int</code> representing the choice that was read.
     */
    public static int readChoose(int min, int max) {
        return readIntegerBetween(INSERT_REQUEST, min, max, m -> choosePrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double.
     *
     * @param message The message to print.
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDouble(String message) {
        return readDouble(message, m -> defaultPrint(m));
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double.
     *
     * @param message The message to print.
     * @param print   Let you execute the print statement that you like the best
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDouble(String message, Consumer<? super String> print) {
        boolean isDouble;
        double read = Double.NaN;

        do {
            print.accept(message);

            try {
                read = reader.nextDouble();
                isDouble = true;
            } catch (InputMismatchException e) {
                PrettyStrings.printlnError(DOUBLE_FORMAT_ERROR);
                isDouble = false;
            } finally {
                flushReader();
            }
        } while (!isDouble);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double
     * or if the double inserted isn't greater than or equal to <code>min</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDoubleWithMinimum(String message, double min) {
        return readDoubleWithMinimum(message, min, InputData::defaultPrint);
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double
     * or if the double inserted isn't greater than or equal to <code>min</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @param print   Let you execute the print statement that you like the best
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDoubleWithMinimum(String message, double min, Consumer<? super String> print) {
        boolean isAboveMin = false;
        double read;

        do {
            read = readDouble(message, print);

            if (read >= min) {
                isAboveMin = true;
            } else {
                PrettyStrings.printlnError(MINIMUM_ERROR_DOUBLE, min);
            }
        } while (!isAboveMin);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double
     * or if the double inserted isn't less than or equal to <code>max</code>.
     *
     * @param message The message to print.
     * @param max     The maximum value to read.
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDoubleWithMaximum(String message, double max) {
        return readDoubleWithMaximum(message, max, InputData::defaultPrint);
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double
     * or if the double inserted isn't less than or equal to <code>max</code>.
     *
     * @param message The message to print.
     * @param max     The maximum value to read.
     * @param print   Let you execute the print statement that you like the best
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDoubleWithMaximum(String message, double max, Consumer<? super String> print) {
        boolean isBelowMax = false;
        double read;

        do {
            read = readDouble(message, print);

            if (read <= max) {
                isBelowMax = true;
            } else {
                PrettyStrings.printlnError(MAXIMUM_ERROR_DOUBLE, max);
            }
        } while (!isBelowMax);

        return read;
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double
     * or if the double inserted isn't between or equal to <code>min</code> and
     * <code>max</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @param max     The maximum value to read.
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDoubleBetween(String message, double min, double max) {
        return readDoubleBetween(message, min, max, InputData::defaultPrint);
    }

    /**
     * Prints <code>message</code> in the terminal and reads the text inserted by
     * the user. It will print an error message if the text inserted isn't a double
     * or if the double inserted isn't between or equal to <code>min</code> and
     * <code>max</code>.
     *
     * @param message The message to print.
     * @param min     The minimum value to read.
     * @param max     The maximum value to read.
     * @param print   Let you execute the print statement that you like the best
     * @return A <code>double</code> representing the double that was read.
     */
    public static double readDoubleBetween(String message, double min, double max, Consumer<? super String> print) {
        boolean isBetweenMinMax = false;
        double read;

        do {
            read = readDouble(message, print);

            if (read < min) {
                PrettyStrings.printlnError(MINIMUM_ERROR_DOUBLE, min);
            } else if (read > max) {
                PrettyStrings.printlnError(MAXIMUM_ERROR_DOUBLE, max);
            } else {
                isBetweenMinMax = true;
            }
        } while (!isBetweenMinMax);

        return read;
    }

    /**
     * Prints <code>question</code> in the terminal with the string " [Y/n] "
     * added. If the user answers with 'y' or 'Y' the method will return
     * <code>true</code>, <code>false</code> otherwise.
     *
     * @param question The question to print.
     * @return A <code>boolean</code> representing the affirmative or negative
     *         answer of the user.
     */
    public static boolean readYesOrNo(String question) {
        return readYesOrNo(question, InputData::yesOrNoPrint);
    }

    /**
     * Prints <code>question</code> in the terminal with the string " [Y/n] "
     * added. If the user answers with 'y' or 'Y' the method will return
     * <code>true</code>, <code>false</code> otherwise.
     *
     * @param question The question to print.
     * @param print    Let you execute the print statement that you like the best
     * @return A <code>boolean</code> representing the affirmative or negative
     *         answer of the user.
     */
    public static boolean readYesOrNo(String question, Consumer<? super String> print) {
        while (true) {
            String answer = readStringNotEmpty(question, true, print);

            YesNoResponse response = parseYesNoResponse(answer);

            switch (response) {
                case YES:
                    return true;
                case NO:
                    return false;
                case INVALID:
                    PrettyStrings.printlnError(INVALID_ANSWER);
                    break;
            }
        }
    }

    /**
     * Enum to represent the possible responses to a yes/no question.
     */
    private enum YesNoResponse {
        YES, NO, INVALID
    }

    /**
     * Parses a string response and determines if it represents yes, no, or invalid
     * input.
     * 
     * @param answer The user's input string
     * @return YesNoResponse enum value representing the parsed response
     */
    private static YesNoResponse parseYesNoResponse(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            return YesNoResponse.INVALID;
        }

        String normalized = answer.trim().toLowerCase();

        // Check for yes responses
        if (normalized.equals("y") || normalized.equals("yes")) {
            return YesNoResponse.YES;
        }

        // Check for no responses
        if (normalized.equals("n") || normalized.equals("no")) {
            return YesNoResponse.NO;
        }

        return YesNoResponse.INVALID;
    }

    /**
     * Alternative implementation using a more functional approach with validation.
     * This method provides additional flexibility for extending accepted responses.
     */
    public static boolean readYesOrNoAdvanced(String question, Consumer<? super String> print) {
        final Set<String> yesResponses = Set.of("y", "yes", "true", "1");
        final Set<String> noResponses = Set.of("n", "no", "false", "0");

        while (true) {
            String answer = readStringNotEmpty(question, true, print);
            String normalized = answer.trim().toLowerCase();

            if (yesResponses.contains(normalized)) {
                return true;
            } else if (noResponses.contains(normalized)) {
                return false;
            } else {
                PrettyStrings.printlnError(INVALID_ANSWER + " Please enter Y/Yes or N/No.");
            }
        }
    }

    private static void defaultPrint(String message) {
        System.out.printf("%s%s", message, INSERT_REQUEST);
    }

    private static void choosePrint(String message) {
        System.out.printf("%s", message);
    }

    private static void yesOrNoPrint(String message) {
        System.out.printf("%s? %s ", message,  "[Y/n]");
    }
}