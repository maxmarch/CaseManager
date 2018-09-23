package com.mpoznyak.domain.model;

public enum Color {
    RED("#ff6f69", "Red"), BLUE("#0392cf", "Blue"), GREEN("#88d8b0", "Green"), YELLOW("#ffeead", "Yellow"),
    ORANGE("#f37735", "Orange"), PINK("#ffaaa5", "Pink"), POWDER_BLUE("#80AEC3", "Powder Blue"),
    VIOLET("#8874a3", "Violet"), COCOA("#c68642", "Cocoa"), ALMOND_CREAM("#CBB2A9", "Almond Cream"),
    SOFT_WHITE("#DFD7CD", "Soft White");
    private final String hexColorCode;
    private final String colorName;

    Color(String hex, String name) {
        colorName = name;
        hexColorCode = hex;
    }

    public String getHexColorCode() {
        return hexColorCode;
    }

    public String getColorName() {
        return colorName;
    }

}


