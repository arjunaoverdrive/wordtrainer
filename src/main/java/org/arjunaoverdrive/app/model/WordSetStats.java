package org.arjunaoverdrive.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "word_set_stats")
public class WordSetStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id", referencedColumnName = "id")
    private WordSet wordSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practiced_by", referencedColumnName = "id")
    private User practicedBy;

    private String language;

    @Column(name = "accuracy", columnDefinition = "FLOAT DEFAULT 0.0")
    private Float accuracy;

    @Column(name = "practiced_at", columnDefinition = "DATETIME")
    private Timestamp practicedAt;

    @OneToMany(mappedBy = "wordSetStats", cascade = CascadeType.ALL)
    private Set<WordStat> wordStats;


    public WordSetStats() {
    }

    public WordSetStats(long id, WordSet wordSet, User practicedBy, String language, Float accuracy, Timestamp practicedAt
            , Set<WordStat> wordStats
    ) {
        this.id = id;
        this.wordSet = wordSet;
        this.practicedBy = practicedBy;
        this.language = language;
        this.accuracy = accuracy;
        this.practicedAt = practicedAt;
        this.wordStats = wordStats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WordSet getWordSet() {
        return wordSet;
    }

    public void setWordSet(WordSet wordSet) {
        this.wordSet = wordSet;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Timestamp getPracticedAt() {
        return practicedAt;
    }

    public void setPracticedAt(Timestamp practicedAt) {
        this.practicedAt = practicedAt;
    }

    public Set<WordStat> getWordStats() {
        return wordStats;
    }

    public void setWordStats(Set<WordStat> wordStats) {
        this.wordStats = wordStats;
    }

    public User getPracticedBy() {
        return practicedBy;
    }

    public void setPracticedBy(User practicedBy) {
        this.practicedBy = practicedBy;
    }
}
