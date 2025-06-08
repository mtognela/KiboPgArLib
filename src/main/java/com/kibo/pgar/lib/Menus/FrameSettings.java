package com.kibo.pgar.lib.Menus;

import com.kibo.pgar.lib.AnsiClasses.Alignment;

/**
 * <code>Class</code> that let's you specify the settings for framing a
 * <code>String</code>.
 * 
 * @author Alessandro Muscio (Kibo) and Mattia Tognela (mtognela)
 * @version 1.6
 */
public final class FrameSettings {
    private static final char HORIZONTAL_FRAME = '-';
    private static final char VERTICAL_FRAME = '|';
    
    private final int width;
    private final Alignment alignment;
    private final char horizontalFrame;
    private final boolean verticalFrameEnabled;
    private final char verticalFrame;

    /**
     * Creates a new <i>settings</i> instance specifying the width of the frame, its
     * alignment and if the vertical frame is enabled or not. The constructor will
     * automatically set the default vertical and horizontal frame.
     * 
     * @param width The width of the frame.
     * @param alignment The alignment of the frame.
     * @param verticalFrameEnabled If the vertical frame is enabled or not.
     */
    public FrameSettings(int width, Alignment alignment, boolean verticalFrameEnabled) {
        this.width = width;
        this.alignment = alignment;
        this.horizontalFrame = FrameSettings.HORIZONTAL_FRAME;
        this.verticalFrameEnabled = verticalFrameEnabled;
        this.verticalFrame = FrameSettings.VERTICAL_FRAME;
    }

    /**
     * Creates a new <i>settings</i> instance with custom frame characters.
     * 
     * @param width The width of the frame.
     * @param alignment The alignment of the frame.
     * @param verticalFrameEnabled If the vertical frame is enabled or not.
     * @param horizontalFrame The character to use for horizontal frames.
     * @param verticalFrame The character to use for vertical frames.
     */
    public FrameSettings(int width, Alignment alignment, boolean verticalFrameEnabled, 
                        char horizontalFrame, char verticalFrame) {
        this.width = width;
        this.alignment = alignment;
        this.horizontalFrame = horizontalFrame;
        this.verticalFrameEnabled = verticalFrameEnabled;
        this.verticalFrame = verticalFrame;
    }

    public int getWidth() {
        return width;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public char getHorizontalFrame() {
        return horizontalFrame;
    }

    public boolean isVerticalFrameEnabled() {
        return verticalFrameEnabled;
    }

    public char getVerticalFrame() {
        return verticalFrame;
    }
}