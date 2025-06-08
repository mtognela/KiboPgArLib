package com.kibo.pgar.lib.Menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import com.kibo.pgar.lib.Formats.Alignment;
import com.kibo.pgar.lib.Strings.PrettyStrings;

/**
 * A utility class for creating formatted ASCII tables in command-line interfaces.
 * <p>
 * This class provides configurable table formatting with features including:
 * <ul>
 *   <li>Customizable borders and separators</li>
 *   <li>Alignment control (left, right, center)</li>
 *   <li>Automatic column width calculation</li>
 *   <li>Header support</li>
 *   <li>Vertical line visibility toggle</li>
 * </ul>
 * </p>
 * <p>
 * 
 * @author Alessandro Muscio (Kibo) and Mattia Tognela (mtognela)
 * @version 1.4
 */
public final class CommandLineTable {
    private static final String SHORT_ROWS_ERROR = PrettyStrings.errorDefine("One or multiple of the given rows are too short for this table!");

    private static final char HORIZONTAL_SEPARATOR = '-';
    private static final char VERTICAL_SEPARATOR = '|';
    private static final char[] JOIN_SEPARATOR = new char[] { ' ', '+' };

    private boolean showVLines;
    private Alignment cellsAlignment;
    private char horizontalSeparator;
    private char verticalSeparator;
    private char[] joinSeparator;
    private List<String> headers;
    private List<List<String>> rows;

    public CommandLineTable() {
        this.showVLines = false;
        this.cellsAlignment = Alignment.LEFT;
        this.horizontalSeparator = CommandLineTable.HORIZONTAL_SEPARATOR;
        this.verticalSeparator = CommandLineTable.VERTICAL_SEPARATOR;
        this.joinSeparator = CommandLineTable.JOIN_SEPARATOR;
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public boolean isShowVLines() {
        return showVLines;
    }

    public void setShowVLines(boolean showVLines) {
        this.showVLines = showVLines;
    }

    public Alignment getCellsAlignment() {
        return cellsAlignment;
    }

    public void setCellsAlignment(Alignment cellsAlignment) {
        this.cellsAlignment = cellsAlignment;
    }

    public void setHorizontalSeparator(char horizontalSeparator) {
        this.horizontalSeparator = horizontalSeparator;
    }

    public void setVerticalSeparator(char verticalSeparator) {
        this.verticalSeparator = verticalSeparator;
    }

    public void setJoinSeparator(char joinSeparator) {
        this.joinSeparator = new char[] { ' ', joinSeparator };
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }

    public void addHeaders(String... headers) {
        this.headers.addAll(Arrays.asList(headers));

        List<String> holes = new ArrayList<>();
        for (int i = 0; i < headers.length; i++)
            holes.add("");

        for (List<String> row : this.rows)
            row.addAll(holes);
    }

    public void addRows(List<List<String>> rows) throws RuntimeException {
        if (this.headers.isEmpty())
            throw new RuntimeException(CommandLineTable.SHORT_ROWS_ERROR);

        for (List<String> row : rows) {
            if (row.size() != this.headers.size())
                throw new RuntimeException(CommandLineTable.SHORT_ROWS_ERROR);
        }

        this.rows.addAll(rows);
    }

    @Override
    public String toString() {
        StringJoiner stringedTable = new StringJoiner("\n");

        List<List<String>> table = new ArrayList<>();
        table.add(this.headers);
        table.addAll(this.rows);

        int[] widths = getMaxWidthsPerColumn(table);

        String hFrame = buildHorizontalFrame(widths);

        for (List<String> row : table) {
            stringedTable.add(hFrame);

            StringJoiner rowJoiner = new StringJoiner(this.showVLines ? "" : " ");
            for (int i = 0; i < row.size(); i++) {
                String cell = row.get(i);

                if (i == 0 && this.showVLines)
                    rowJoiner.add(String.valueOf(this.verticalSeparator));

                if (this.cellsAlignment.equals(Alignment.CENTER))
                    rowJoiner.add(PrettyStrings.center(cell, widths[i]));
                else
                    rowJoiner.add(PrettyStrings.column(cell, widths[i], this.cellsAlignment.getIndex() < 0));

                if (this.showVLines)
                    rowJoiner.add(String.valueOf(this.verticalSeparator));
            }

            stringedTable.add(rowJoiner.toString());
        }

        stringedTable.add(hFrame);

        return stringedTable.toString();
    }

    private int[] getMaxWidthsPerColumn(List<List<String>> table) {
        int[] widths = new int[this.headers.size()];

        for (int i = 0; i < this.headers.size(); i++)
            widths[i] = 0;

        for (List<String> row : table) {
            for (int i = 0; i < row.size(); i++)
                widths[i] = Math.max(
                        widths[i],
                        row.get(i).length() + (this.cellsAlignment.equals(Alignment.CENTER) ? 2 : 1));
        }

        return widths;
    }

    private String buildHorizontalFrame(int[] widths) {
        StringBuffer frame = new StringBuffer();

        for (int i = 0; i < widths.length; i++) {
            int width = widths[i];
            StringBuffer framePiece = new StringBuffer();

            if (i == 0 && this.showVLines)
                framePiece.append(this.joinSeparator[1]);

            framePiece.append(PrettyStrings.repeatChar(this.horizontalSeparator, width));

            framePiece.append(this.joinSeparator[this.showVLines ? 1 : 0]);

            frame.append(framePiece.toString());
        }

        return frame.toString();
    }
}
