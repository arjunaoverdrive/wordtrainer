package org.arjunaoverdrive.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String word;
    @Column(nullable = false)
    private String translation;
    @Column(columnDefinition = "INT DEFAULT 0") //the higher the value -- the more mistakes have been made
    private int correctness;
    @Column(columnDefinition = "INT DEFAULT 0", name = "translation_correctness")
    private int translationCorrectness;
    @ManyToOne
    @JoinColumn(name = "set_id", referencedColumnName = "id")
    @JsonIgnore
    private WordSet wordSet;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public WordSet getWordSet() {
        return wordSet;
    }

    public void setWordSet(WordSet wordSet) {
        this.wordSet = wordSet;
    }

    public int getCorrectness() {
        return correctness;
    }

    public void setCorrectness(Integer correctness) {
        this.correctness = correctness;
    }

    public int getTranslationCorrectness() {
        return translationCorrectness;
    }

    public void setTranslationCorrectness(int translationCorrectness) {
        this.translationCorrectness = translationCorrectness;
    }

    @Override
    public String toString() {
        return "{\"word\":\"" + word + "\"" +
                ",\n\"translation\":\"" + translation + "\"" +
                "}";
    }
}
