package org.arjunaoverdrive.app.model;

public enum Language {
    ENGLISH("en", "English"),
    RUSSIAN("ru", "Русский"),
    DEUTSCH("de", "Deutsch"),
    FRANCAIS("fr", "Francais"),
    SPANISH("sp", "Spanish");


    private String locale;
    private String language;

    Language(String locale, String language) {
        this.locale = locale;
        this.language = language;
    }

    public String getLocale() {
        return locale;
    }

    public String getLanguage() {
        return language;
    }
}
