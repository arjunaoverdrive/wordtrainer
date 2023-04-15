package org.arjunaoverdrive.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "word_stat")
public class WordStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "word_id")
    private long wordId;
    private int rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_stats_id", referencedColumnName = "id")
    private WordSetStats wordSetStats;

    public WordStat() {
    }

    public WordStat(long id, long wordId, int rate, WordSetStats wordSetStats) {
        this.id = id;
        this.wordId = wordId;
        this.rate = rate;
        this.wordSetStats = wordSetStats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public WordSetStats getWordSetStats() {
        return wordSetStats;
    }

    public void setWordSetStats(WordSetStats wordSetStats) {
        this.wordSetStats = wordSetStats;
    }
}
