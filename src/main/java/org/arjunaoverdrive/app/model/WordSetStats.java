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
    @Column(name = "set_id")
    private Integer setId;
    @Column(name = "user_id")
    private long userId;
    private int lang;
    @Column(name = "accuracy", columnDefinition = "FLOAT DEFAULT 0.0")
    private Float accuracy;
    @Column(name = "practiced_at", columnDefinition = "DATETIME")
    private Timestamp practicedAt;

    @OneToMany(mappedBy = "wordSetStats", cascade = CascadeType.ALL)
    private Set<WordStat> wordStats;

    public WordSetStats() {
    }

    public WordSetStats(long id, Integer setId, long userId, int lang, Float accuracy, Timestamp practicedAt, Set<WordStat> wordStats) {
        this.id = id;
        this.setId = setId;
        this.userId = userId;
        this.lang = lang;
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

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
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
}
