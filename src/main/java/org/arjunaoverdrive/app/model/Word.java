package org.arjunaoverdrive.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String word;
    @Column(nullable = false)
    private String translation;
    @Column(columnDefinition = "FLOAT DEFAULT 0.0", name = "src_rate") //the higher the value -- the more mistakes have been made
    private float srcRate;
    @Column(columnDefinition = "FLOAT DEFAULT 0.0", name = "target_rate")
    private float trgtRate;
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

    public float getSrcRate() {
        return srcRate;
    }

    public void setSrcRate(float srcRate) {
        this.srcRate = srcRate;
    }

    public float getTrgtRate() {
        return trgtRate;
    }

    public void setTrgtRate(float trgtRate) {
        this.trgtRate = trgtRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return getId() == word1.getId() && getWord().equals(word1.getWord()) && getTranslation().equals(word1.getTranslation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWord(), getTranslation());
    }

    @Override
    public String toString() {
        return "{\"word\":\"" + word + "\"" +
                ",\n\"translation\":\"" + translation + "\"" +
                "}";
    }
}
