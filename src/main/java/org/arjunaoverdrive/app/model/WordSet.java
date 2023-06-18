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

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME")
    private Timestamp createdAt;

    @Column(name = "src_lang", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Language sourceLanguage;

    @Column(name = "trgt_lang", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Language targetLanguage;

    @OneToMany(mappedBy = "wordSet", cascade = CascadeType.ALL)
    private List<Word>wordList;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "wordSet", cascade = CascadeType.ALL)
    private List<WordSetStats> wordSetStats;


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public List<WordSetStats> getWordSetStats() {
        return wordSetStats;
    }

    public void setWordSetStats(List<WordSetStats> wordSetStats) {
        this.wordSetStats = wordSetStats;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @Override
    public int compareTo(WordSet ws) {
        return ws.getCreatedAt().compareTo(createdAt);
    }
}
