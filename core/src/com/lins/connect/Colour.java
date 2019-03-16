package com.lins.connect;

import com.badlogic.gdx.graphics.Color;

import static com.badlogic.gdx.math.MathUtils.random;

public final class Colour {
    public static final Color GREEN_SEA = new Color(0x16a085ff);
    public static final Color NEPHRITIS = new Color(0x27ae60ff);
    public static final Color BELIZE_HOLE = new Color(0x2980b9ff);
    public static final Color WISTERIA = new Color(0x8e44adff);
    public static final Color MIDNIGHT_BLUE = new Color(0x2c3e50ff);
    public static final Color ORANGE = new Color(0xf39c12ff);
    public static final Color PUMPKIN = new Color(0xd35400ff);
    public static final Color POMEGRANATE = new Color(0xc0392bff);
    public static final Color SILVER = new Color(0xbdc3c7ff);
    public static final Color ASBESTOS = new Color(0x7f8c8dff);

    public static final Color[] COLORS = {
            //GREEN_SEA, NEPHRITIS, BELIZE_HOLE, WISTERIA, MIDNIGHT_BLUE, ORANGE, PUMPKIN, POMEGRANATE
            NEPHRITIS, BELIZE_HOLE, WISTERIA, MIDNIGHT_BLUE, ORANGE, POMEGRANATE
    };

    public static Color random() {
        return COLORS[random.nextInt(COLORS.length)];
    }

    public static Color lighten(final Color color) {
        return new Color(color).lerp(Color.GRAY, .2f).mul(1.5f);
    }

    public static Color darken(final Color color) {
        return new Color(color).lerp(Color.BLACK, .2f);
    }

    public static boolean isUAcolors(Color color1, Color color2) {
        return color1 == Colour.ORANGE && color2 == Colour.BELIZE_HOLE ||
               color2 == Colour.ORANGE && color1 == Colour.BELIZE_HOLE;
    }
}