package com.backend.hl.model;

public class ColorTheme {
    private String colorName;
    private Colors lightColors;
    private Colors darkColors;

    public static class Colors {
        private String main;
        private String container;
        private String onContainer;

        public Colors(String main, String container, String onContainer) {
            this.main = main;
            this.container = container;
            this.onContainer = onContainer;
        }

        public String getMain() {
            return main;
        }

        public String getContainer() {
            return container;
        }

        public String getOnContainer() {
            return onContainer;
        }
    }

    public ColorTheme(String colorName, Colors lightColors, Colors darkColors) {
        this.colorName = colorName;
        this.lightColors = lightColors;
        this.darkColors = darkColors;
    }

    public String getColorName() {
        return colorName;
    }

    public Colors getLightColors() {
        return lightColors;
    }

    public Colors getDarkColors() {
        return darkColors;
    }
}
