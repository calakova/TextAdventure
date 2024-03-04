package at.bbrz;

import lombok.Getter;

import java.security.InvalidParameterException;

public enum OutputColors {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m");

    @Getter
    private final String label;

    OutputColors(String label) {
        this.label = label;
    }

    public static OutputColors findColor(String color) throws InvalidParameterException {
        String colorUpperCase = color.toUpperCase();
        OutputColors result = null;
        for (OutputColors outputColor : values()) {
            if (outputColor.name().equals(colorUpperCase)) {
                result = outputColor;
                break;
            }
        }

        if (result == null || result == RESET) {
            throw new InvalidParameterException(color + " is not a valid color!");
        }

        return result;
    }

    public static String getResetLabel() {
        return RESET.label;
    }
}
