package org.arjunaoverdrive.app.model;

import java.util.Arrays;

public enum Language {
    ENGLISH("en", "English"),
    RUSSIAN("ru", "Русский"),
    DEUTSCH("de", "Deutsch"),
    FRANCAIS("fr", "Francais"),
    SPANISH("sp", "Spanish");


    private final String locale;
    private final String language;

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

    public static Language getLanguage(String language){
        return Arrays.stream(Language.values())
                .filter(l -> l.getLanguage().equals(language))
                .findFirst()
                .get();
    }
}
