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
    @Column(name = "src_times_practiced", columnDefinition = "INT DEFAULT 0")
    private int srcTimesPracticed;
    @Column(name = "src_lang_accuracy", columnDefinition = "FLOAT DEFAULT 0.0")
    private float srcLangAccuracy;
    @Column(name = "created_on", nullable = false, columnDefinition = "DATETIME")
    private Timestamp createdOn;
    @Column(name = "last_practiced", columnDefinition = "DATETIME")
    private Timestamp lastPracticed;
    @Column(name = "target_lang_accuracy", columnDefinition = "FLOAT DEFAULT 0.0")
    private float targetLangAccuracy;
    @Column(name = "target_times_practiced", columnDefinition = "INT DEFAULT 0")
    private int targetTimesPracticed;

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

    public float getSrcLangAccuracy() {
        return srcLangAccuracy;
    }

    public void setSrcLangAccuracy(float srcLangAccuracy) {
        this.srcLangAccuracy = srcLangAccuracy;
    }

    public int getSrcTimesPracticed() {
        return srcTimesPracticed;
    }

    public void setSrcTimesPracticed(int srcTimesPracticed) {
        this.srcTimesPracticed = srcTimesPracticed;
    }

    public Timestamp getLastPracticed() {
        return lastPracticed;
    }

    public void setLastPracticed(Timestamp lastPracticed) {
        this.lastPracticed = lastPracticed;
    }

    public float getTargetLangAccuracy() {
        return targetLangAccuracy;
    }

    public void setTargetLangAccuracy(float targetLangAccuracy) {
        this.targetLangAccuracy = targetLangAccuracy;
    }

    public int getTargetTimesPracticed() {
        return targetTimesPracticed;
    }

    public void setTargetTimesPracticed(int targetTimesPracticed) {
        this.targetTimesPracticed = targetTimesPracticed;
    }

    @Override
    public int compareTo(WordSet ws) {
        return ws.getCreatedOn().compareTo(createdOn);
    }
}
