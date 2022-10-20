package org.arjunaoverdrive.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class WordSet implements Comparable<WordSet>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(name = "times_practiced", columnDefinition = "INT DEFAULT 0")
    private int timesPracticed;
    private float accuracy;
    @Column(name = "created_on", nullable = false, columnDefinition = "DATETIME")
    private Timestamp createdOn;
    @Column(name = "last_practiced", columnDefinition = "DATETIME")
    private Timestamp lastPracticed;
    @Column(name = "translation_accuracy", columnDefinition = "FLOAT DEFAULT 0.0")
    private float translationAccuracy;
    @Column(name = "translation_times_practiced", columnDefinition = "INT DEFAULT 0")
    private int translationTimesPracticed;

    @OneToMany(mappedBy = "wordSet", cascade = CascadeType.ALL)
    private List<Word>wordList;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public int getTimesPracticed() {
        return timesPracticed;
    }

    public void setTimesPracticed(int timesPracticed) {
        this.timesPracticed = timesPracticed;
    }

    public Timestamp getLastPracticed() {
        return lastPracticed;
    }

    public void setLastPracticed(Timestamp lastPracticed) {
        this.lastPracticed = lastPracticed;
    }

    public float getTranslationAccuracy() {
        return translationAccuracy;
    }

    public void setTranslationAccuracy(float translationAccuracy) {
        this.translationAccuracy = translationAccuracy;
    }

    public int getTranslationTimesPracticed() {
        return translationTimesPracticed;
    }

    public void setTranslationTimesPracticed(int translationTimesPracticed) {
        this.translationTimesPracticed = translationTimesPracticed;
    }

    @Override
    public int compareTo(WordSet ws) {
        return ws.getCreatedOn().compareTo(createdOn);
    }
}
