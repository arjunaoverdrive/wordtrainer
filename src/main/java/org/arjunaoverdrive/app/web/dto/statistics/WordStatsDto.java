package org.arjunaoverdrive.app.web.dto.statistics;

public class WordStatsDto {
    private String word;
    private String translation;
    private float rateSource;
    private float rateTarget;

    public WordStatsDto() {
    }

    public WordStatsDto(String word, String translation, float rateSource, float rateTarget) {
        this.word = word;
        this.translation = translation;
        this.rateSource = rateSource;
        this.rateTarget = rateTarget;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public float getRateSource() {
        return rateSource;
    }

    public void setRateSource(float rateSource) {
        this.rateSource = rateSource;
    }

    public float getRateTarget() {
        return rateTarget;
    }

    public void setRateTarget(float rateTarget) {
        this.rateTarget = rateTarget;
    }
}
